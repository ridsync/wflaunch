package com.rasset.wflaunch.ui

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import com.rasset.wflaunch.R
import com.rasset.wflaunch.core.AppConst
import com.rasset.wflaunch.network.res.BaseModel
import com.rasset.wflaunch.utils.Logger
import com.rasset.wflaunch.utils.Prefer
import com.rasset.wflaunch.utils.showToast
import kotlinx.android.synthetic.main.activity_splash.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [testDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class SplashActivity : BaseActivity() {
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({

            val first = Prefer.getPreferenceBoolean(AppConst.PREFERENCE_FIRST_LAUNCH,mContext)
            if (!first){
                startActivity(TutorialActivity.newIntent(mContext))
                finish()
                Prefer.setSharedPreference(AppConst.PREFERENCE_FIRST_LAUNCH,true,mContext)
                return@postDelayed
            }

            val userId = Prefer.getPreferenceLong(AppConst.PREFERENCE_USERINFO_ID,mContext)
            if ( userId > 0) {
                startActivity(MainActivity.newIntent(mContext))
            } else {
                startActivity(LoginActivity.newIntent(mContext))
            }
            finish()
        },1700)
//        try {
//            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
//            for (signature in info.signatures) {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//            }
//        } catch ( err: PackageManager.NameNotFoundException) {
//            err.printStackTrace()
//        } catch ( err: NoSuchAlgorithmException) {
//            err.printStackTrace()
//        }
    }

    private fun postUserLogin(lId: Long, lFirstSeq: Long) {
//        val task = MainListTask(applicationContext, ReqType.REQUEST_TYPE_GET_USER_LIST, this)
//        task.addParam(ParamKey.PARAM_LISTTYPE, 1) // to server 1234
//        task.addParam(ParamKey.PARAM_FIRSTSEQ, lFirstSeq)
//        NetManager.startTask(task)
    }

    override fun onNetSuccess(data: BaseModel?, nReqType: Int) {
        Logger.d("onNetSuccess  ")
    }

    override fun onNetFail(retCode: Int, strErrorMsg: String, nReqType: Int) {
        super.onNetFail(retCode, strErrorMsg, nReqType)
        Logger.d("onNetFail  ")
    }

    override fun onProgresStart(nReqType: Int) {
        Logger.d("onProgresStart  ")
    }

    override fun onProgresStop(nReqType: Int) {
        Logger.d("onProgresStop  ")
    }

}


