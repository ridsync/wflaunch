package com.rasset.wflaunch.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.view.WindowManager
import com.rasset.wflaunch.R
import com.rasset.wflaunch.ui.adapter.HomeAdapter
import com.rasset.wflaunch.ui.components.Customization
import com.rd.PageIndicatorView
import com.rd.animation.type.AnimationType
import kotlinx.android.synthetic.main.activity_tutorial.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [testDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class TutorialActivity : BaseActivity() {

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, TutorialActivity::class.java)
            return intent
        }
    }
    private var customization: Customization? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        customization = Customization()

//        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        initViews()
        updateIndicator()
    }

    private fun initViews() {
        val adapter = HomeAdapter()
        adapter.setData(createPageList())

        val pager = viewPager
        pager.adapter = adapter


        pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if(position == 2){
                    BTN_NEXT.visibility = View.VISIBLE
                } else {
                    BTN_NEXT.visibility = View.INVISIBLE
                }
            }

        })

        BTN_NEXT.setOnClickListener{
            startActivity(MainActivity.newIntent(mContext))
            finish()
        }
    }

    private fun createPageList(): List<View> {
        val pageList = ArrayList<View>()
        pageList.add(createPageView(R.drawable.t1))
        pageList.add(createPageView(R.drawable.t2))
        pageList.add(createPageView(R.drawable.t3))

        return pageList
    }

    private fun createPageView(resId: Int): View {
        val view = View(this)
        view.setBackgroundResource(resId)

        return view
    }

    private fun updateIndicator() {
        if (customization == null) {
            return
        }

        pageIndicatorView.setAnimationType(customization!!.getAnimationType())
        pageIndicatorView.setOrientation(customization!!.getOrientation())
        pageIndicatorView.setRtlMode(customization!!.getRtlMode())
        pageIndicatorView.setInteractiveAnimation(customization!!.isInteractiveAnimation())
        pageIndicatorView.setAutoVisibility(customization!!.isAutoVisibility())
        pageIndicatorView.setFadeOnIdle(customization!!.isFadeOnIdle())
    }
}


