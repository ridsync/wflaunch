package com.rasset.wflaunch.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.rasset.wflaunch.R
import com.rasset.wflaunch.model.DiagnoseInfo
import com.rasset.wflaunch.network.res.BaseModel
import com.rasset.wflaunch.ui.DiagAttentionActivity
import com.rasset.wflaunch.utils.Logger
import com.rasset.wflaunch.utils.dpToPx
import kotlinx.android.synthetic.main.fragment_diag_step_second.*
import java.util.HashMap

/**
 * Created by devok on 2018-09-05.
 */

class DiagSubStepSecondFragment : BaseFragment() {

    private object Holder { val INSTANCE = DiagSubStepSecondFragment() }

    interface UpdateDiagnoseDatas {
        fun isValidDiagInputs(): Boolean
        fun getDiagDatas():DiagnoseInfo
    }

    companion object {
        val singleTone: DiagSubStepSecondFragment by lazy { Holder.INSTANCE }

        val instance: DiagSubStepSecondFragment by lazy { DiagSubStepSecondFragment() }

        fun newInstance(context: Context): Intent {
            val intent = Intent(context, DiagSubStepSecondFragment::class.java)
            return intent
        }
    }

    lateinit var tabLayout: TabLayout
    var fragments: HashMap<Int, SurveyBaseFragment> = HashMap()
    var selectedAdviser:DiagSubStepFirstFragment.ADVISOR = DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_INVEST
    var selectedSubCategory:DiagSubStepFirstFragment.SURV_DIAGTYPE = DiagSubStepFirstFragment.SURV_DIAGTYPE.SURV_TYPE_INVEST_SELL

    private var previousVPposition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_diag_step_second, container, false)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isFirstInit){
            isFirstInit = true
            initFirst()
        }
    }

    override fun onStop() {
        super.onStop()
    }

    private fun initFirst(){

//        selectedAdviser = (activity as DiagAttentionActivity).selectedAdviser
//        selectedSubCategory = (activity as DiagAttentionActivity).selectedSubCategory

        setupTablayout()
        setupMainViewPager()
        // Transition 문제회피
        VP_DIAG_SURVEYS.translationY = 300f
        Handler().postDelayed({
            VP_DIAG_SURVEYS.visibility = View.VISIBLE
            VP_DIAG_SURVEYS.alpha = 0.5f
            VP_DIAG_SURVEYS.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .setDuration(80)
                    .start()
        },500)
    }

    private fun setupTablayout() {

        val digCategorise = resources.getStringArray(R.array.diag_categorys)

        tabLayout = TBL_TAB_MENUS

        tabLayout.removeAllTabs()
        tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.main_text_primary))
        tabLayout.setTabTextColors(resources.getColor(R.color.main_text_secondary), resources.getColor(R.color.main_text_primary))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        when (selectedAdviser) {
            DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_INVEST -> {
                if (selectedSubCategory == DiagSubStepFirstFragment.SURV_DIAGTYPE.SURV_TYPE_INVEST_SELL){
                    tabLayout.addTab(tabLayout.newTab().setText(digCategorise[0]))
                } else if (selectedSubCategory == DiagSubStepFirstFragment.SURV_DIAGTYPE.SURV_TYPE_INVEST_BUY ) {
                    tabLayout.addTab(tabLayout.newTab().setText(digCategorise[1]))
                } else {
                    tabLayout.addTab(tabLayout.newTab().setText(digCategorise[0]))
                    tabLayout.addTab(tabLayout.newTab().setText(digCategorise[1]))
                }
            }
            DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_MD -> {
                tabLayout.addTab(tabLayout.newTab().setText(digCategorise[2]))
            }
            DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_TAX-> {
                if (selectedSubCategory == DiagSubStepFirstFragment.SURV_DIAGTYPE.SURV_TYPE_TAX_ASSET){
                    tabLayout.addTab(tabLayout.newTab().setText(digCategorise[3]))
                } else if (selectedSubCategory == DiagSubStepFirstFragment.SURV_DIAGTYPE.SURV_TYPE_TAX_FALM ) {
                    tabLayout.addTab(tabLayout.newTab().setText(digCategorise[4]))
                } else {
                    tabLayout.addTab(tabLayout.newTab().setText(digCategorise[3]))
                    tabLayout.addTab(tabLayout.newTab().setText(digCategorise[4]))
                }
            }
            DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_HOME_INTE-> {
                tabLayout.addTab(tabLayout.newTab().setText(digCategorise[5]))
            }
            DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_MANAGEMENT-> {
                tabLayout.addTab(tabLayout.newTab().setText(digCategorise[6]))
            }
            DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_CM-> {
                tabLayout.addTab(tabLayout.newTab().setText(digCategorise[7]))
            }
        }
        for (i in 0 until tabLayout.tabCount) {
            val tab = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, 0, dpToPx(mContext, 50).toInt(), 0)
            tab.requestLayout()
        }
    }

    private fun setupMainViewPager() {

        //        coordLayout = (CoordinatorLayout) mRootView.findViewById(R.id.coordinatorLayout);
        //        VP_DIAG_SURVEYS = (GestureViewPager) mRootView.findViewById(R.id.VP_MAIN_CHAT_LIST);
        fragmentManager?.apply {
            val adapter = MainFragmentPagerAdapter(this, tabLayout.tabCount)
            VP_DIAG_SURVEYS.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
            VP_DIAG_SURVEYS.adapter = adapter
            VP_DIAG_SURVEYS.setSwipeEnabled(false)
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    if (VP_DIAG_SURVEYS == null) return
                    VP_DIAG_SURVEYS.setCurrentItem(tab.position,false)
                    if (previousVPposition != tab.position)
                        previousVPposition = tab.position

                    if (fragments == null) return
                    if (fragments.get(tab.position) is DiagSurveyAssetSellFragment) {
                        //                    ((ChatListSuggestFragment) fragments.get(tab.getPosition())).scrolltoLittleBitListView();
                    } else if (fragments.get(tab.position) is DiagSurveyAssetBuyFragment) {
                        //                    ((ChatListRelationFragment) fragments.get(tab.getPosition())).scrolltoLittleBitListView();
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {

                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                    if (fragments == null) return
                    if (previousVPposition == tab.position) {
                        if (fragments.get(tab.position) is DiagSurveyAssetSellFragment) {
                            //                        ((ChatListSuggestFragment) fragments.get(tab.getPosition())).smoothScrolltoTopListView();
                        } else if (fragments.get(tab.position) is DiagSurveyAssetBuyFragment) {
                            //                        ((ChatListRelationFragment) fragments.get(tab.getPosition())).smoothScrolltoTopListView();
                        }
                    }
                }
            })

        }

    }

    fun getFragmentEachAdvisor(index:Int) = when (selectedAdviser) {
            DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_INVEST -> {
                if(index == 0 ) {
                    if (selectedSubCategory == DiagSubStepFirstFragment.SURV_DIAGTYPE.SURV_TYPE_INVEST_BUY){
                        DiagSurveyAssetBuyFragment()
                    } else {
                        DiagSurveyAssetSellFragment()
                    }
                } else {
                    DiagSurveyAssetBuyFragment()
                }
            }
            DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_MD -> {
                DiagSurveyMDFragment()
            }
            DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_TAX-> {
                if(index == 0 ) {
                    if (selectedSubCategory == DiagSubStepFirstFragment.SURV_DIAGTYPE.SURV_TYPE_TAX_FALM){
                        DiagSurveyTaxFarmLandFragment()
                    } else {
                        DiagSurveyTaxAssetFragment()
                    }
                } else {
                    DiagSurveyTaxFarmLandFragment()
                }
            }
            DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_HOME_INTE-> {
                DiagSurveyHomeInteFragment()
            }
            DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_MANAGEMENT-> {
                DiagSurveyManageFragment()
            }
            DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_CM-> {
                DiagSurveyCMFragment()
            }
    }

    fun getSubFragments(): HashMap<Int, SurveyBaseFragment>{
        return fragments
    }


    override fun onNetSuccess(data: BaseModel?, nReqType: Int) {

    }

    override fun onNetFail(retCode: Int, strErrorMsg: String, nReqType: Int) {
        super.onNetFail(retCode, strErrorMsg, nReqType)
    }

    override fun onProgresStart(nReqType: Int) {

    }

    override fun onProgresStop(nReqType: Int) {

    }


    inner class MainFragmentPagerAdapter(fragmentManager: FragmentManager, tabItemCount: Int) : FragmentStatePagerAdapter(fragmentManager) {
        private var NUM_PAGE_ITEMS = 0

        init {
            NUM_PAGE_ITEMS = tabItemCount
        }

        // Returns total number of pages
        override fun getCount(): Int {
            return NUM_PAGE_ITEMS
        }

        // Returns the fragment to display for that page
        // Adapter내부 인스턴스가 필요한경우만 호출됨. Destroy를 안하도록했기에 최초1회만 호출.
        override fun getItem(position: Int): Fragment {
            var fragment = getFragmentEachAdvisor(position)
            fragments[position] = fragment
            Logger.d("MainFragmentPagerAdapter", "getItem position = " + position + "fragment = " + fragment)
            return fragment
        }

        override fun getItemPosition(`object`: Any): Int {
            Logger.d("MainFragmentPagerAdapter", "getItemPosition = POSITION_NONE  $`object`")
            return PagerAdapter.POSITION_NONE
        }

        // Returns the page title for the top indicator
        override fun getPageTitle(position: Int): CharSequence? {
            return "Page $position"
        }

        override fun destroyItem(container: ViewGroup, position: Int, instance: Any) {
            // Fragment Instance Refresh 처리..., !!!
        }
    }

}