package com.rasset.wflaunch.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.rasset.wflaunch.R
import com.rasset.wflaunch.core.AppConst

import com.rasset.wflaunch.network.NetManager
import com.rasset.wflaunch.network.protocol.ParamKey
import com.rasset.wflaunch.network.protocol.ReqType
import com.rasset.wflaunch.network.res.BaseModel
import com.rasset.wflaunch.network.res.ResContentList
import com.rasset.wflaunch.network.task.MainListTask
import com.rasset.wflaunch.ui.dialog.MainCustomDialog
import com.rasset.wflaunch.ui.fragments.BaseFragment
import com.rasset.wflaunch.ui.fragments.MainSubCustomersFragment
import com.rasset.wflaunch.ui.fragments.MainSubREAssetFragment
import com.rasset.wflaunch.utils.JUtil
import com.rasset.wflaunch.utils.Logger
import com.rasset.wflaunch.utils.Prefer
import com.rasset.wflaunch.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_appbarlayout.*
import android.support.v4.app.ActivityOptionsCompat
import com.rasset.wflaunch.model.DiagnoseInfo
import de.greenrobot.event.EventBus
import kotlinx.android.synthetic.main.item_main_customer.*
import android.view.animation.Animation
import android.R.attr.pivotY
import android.R.attr.pivotX
import android.view.animation.RotateAnimation
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.TypedArray
import android.net.Uri
import android.view.animation.Animation.INFINITE
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.bumptech.glide.Glide
import com.rasset.wflaunch.ui.mapapi.GpsInfo
import java.util.*


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [testDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainActivity : BaseActivity() {

    companion object {

        private val INTENT_USER_ID = "user_id"

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
//            intent.putExtra(INTENT_USER_ID, user.id)
            return intent
        }
    }
    lateinit var  arrMenusTxt:Array<String>
    lateinit var  arrMenusImg: TypedArray
    lateinit var gpsInfo:GpsInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gpsInfo = GpsInfo(this)
        val anim = ObjectAnimator.ofFloat(IV_DART, "rotation", 3600f)
        anim.duration = 1000
        anim.interpolator = LinearInterpolator()
        anim.repeatCount = 100

        arrMenusTxt = resources.getStringArray(R.array.menu_list_txt)
        arrMenusImg = resources.obtainTypedArray(R.array.menu_list_img)

        BTN_WHATFL.setOnClickListener {

            if (anim.isPaused){
                anim.resume()// keep rotation after animation
                TV_MENU_TXT.text = "또 골라요?"
            } else {
                anim.start()
            }
            Glide.with(mContext)
                    .load(R.drawable.dart_trans)
                    .crossFade(300)
                    .into(IV_MENU_IMG)
            IV_DART.visibility = View.VISIBLE
            IV_MENU_IMG.visibility = View.INVISIBLE

            IV_MENU_IMG.postDelayed({

                val menuIndex = Random().nextInt(arrMenusTxt.size)
                val menuTxt = arrMenusTxt[ menuIndex ]
                val menuImg = arrMenusImg.getResourceId(menuIndex,R.drawable.dart_trans)

                Glide.with(mContext)
                        .load(menuImg)
                        .crossFade(500)
                        .placeholder(R.drawable.dart_trans)
                        .into(IV_MENU_IMG)

                TV_MENU_TXT.text = menuTxt
                BTN_GOTOLAUNCH.text = "$menuTxt 먹으러가자!!"
                BTN_WHATFL.visibility = View.GONE
                BTN_GOTOLAUNCH.visibility = View.VISIBLE
                BTN_GOOGLE.visibility = View.VISIBLE
                BTN_RETRY.visibility = View.VISIBLE

                anim.pause()
                IV_MENU_IMG.visibility = View.VISIBLE
                IV_DART.visibility = View.INVISIBLE

            }, 6000)

        }

        BTN_GOTOLAUNCH.setOnClickListener{
//            showToast {
//                "아직 준비중이에요"
//            }
            startDaumMapAct()
        }

        BTN_GOTOLAUNCH.setOnLongClickListener {
            startDaumMapIntentView()
            return@setOnLongClickListener true
        }

        BTN_GOOGLE.setOnClickListener {
            startGoogleMapIntentView()
        }

        BTN_RETRY.setOnClickListener {

            BTN_RETRY.visibility = View.GONE
            BTN_GOTOLAUNCH.visibility = View.GONE
            BTN_GOOGLE.visibility = View.GONE
            BTN_WHATFL.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        showDialog()
    }

    private fun startDaumMapAct (){
        startActivity(DiagAttentionActivity.newIntent(mContext,TV_MENU_TXT.text.toString()))
    }

    private fun startDaumMapIntentView (){
        if (gpsInfo.isGetLocation){
            val uri = Uri.parse("daummaps://search?q=${TV_MENU_TXT.text}&p=${gpsInfo.latitude},${gpsInfo.longitude}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun startGoogleMapIntentView(){
        if (gpsInfo.isGetLocation) {
            val gmmIntentUri = Uri.parse("geo:${gpsInfo.latitude},${gpsInfo.longitude}?z=10&q=${TV_MENU_TXT.text}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun showDialog() {

        val dialog = MainCustomDialog.newInstance(mContext).apply {
            setTitle(R.string.common_alert)
            setMsgContents("종료하실래요?")
            setPositiveButton(R.string.btn_confirm, MainCustomDialog.OnPositvelListener { dialog ->
                if (JUtil.isDoubleClick(dialog.view)) return@OnPositvelListener
                finish()
            })
            setNegativeButton(R.string.btn_cancel, MainCustomDialog.OnNegativelListener { dialog ->
                if (JUtil.isDoubleClick(dialog.view)) return@OnNegativelListener
            })
        }
        dialog.show(supportFragmentManager, AppConst.DIALOG_LOGIN_FAIL)
    }
}

