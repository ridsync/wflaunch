package com.rasset.wflaunch.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rasset.wflaunch.R
import com.rasset.wflaunch.core.AppConst
import com.rasset.wflaunch.network.res.BaseModel
import com.rasset.wflaunch.ui.DiagAttentionActivity
import com.rasset.wflaunch.utils.showToast
import kotlinx.android.synthetic.main.fragment_diag_default_info.*
import kotlinx.android.synthetic.main.fragment_diag_step_first.*

/**
 * Created by devok on 2018-09-05.
 */

class DiagSubStepFirstFragment : BaseFragment() {

    private object Holder { val INSTANCE = DiagSubStepFirstFragment() }
    enum class ADVISOR(val wCode: Long, val wewon: String) {
        ADVISOR_NAME_INVEST(11, AppConst.ADVISOR_NAME_INVEST)
        , ADVISOR_NAME_MD(21, AppConst.ADVISOR_NAME_MD)
        , ADVISOR_NAME_TAX(31, AppConst.ADVISOR_NAME_TAX)
        , ADVISOR_NAME_HOME_INTE(41, AppConst.ADVISOR_NAME_HOME_INTE)
        , ADVISOR_NAME_CM(51, AppConst.ADVISOR_NAME_CM)
        , ADVISOR_NAME_MANAGEMENT(61, AppConst.ADVISOR_NAME_MANAGEMENT)
    }
    enum class SURV_DIAGTYPE(val diagType: String) {
        SURV_TYPE_INVEST_SELL("11")
        , SURV_TYPE_INVEST_BUY("21")
        , SURV_TYPE_INVEST_ALL("31")
        , SURV_TYPE_TAX_ASSET("11")
        , SURV_TYPE_TAX_FALM("21")
        , SURV_TYPE_TAX_ALL("31")
    }
    companion object {
        val singleTone: DiagSubStepFirstFragment by lazy { Holder.INSTANCE }

        val instance: DiagSubStepFirstFragment by lazy { DiagSubStepFirstFragment() }

        fun newInstance(context: Context): Intent {
            val intent = Intent(context, DiagSubStepFirstFragment::class.java)
            return intent
        }
    }

    var selectedAdvisor:ADVISOR = ADVISOR.ADVISOR_NAME_INVEST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_diag_step_first, container, false)
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

//        (activity as DiagAttentionActivity).diagnoseInfo.let { diagnoseInfo ->
//            when(diagnoseInfo.applyPart){
//                DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_INVEST.wCode -> {
//                    setCheckCardViews(RL_ADVISOR_INVEST)
//                    selectedAdvisor = ADVISOR.ADVISOR_NAME_INVEST
//                }
//                DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_MD.wCode -> {
//                    setCheckCardViews(RL_ADVISOR_MD)
//                    selectedAdvisor = ADVISOR.ADVISOR_NAME_MD
//                }
//                DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_TAX.wCode ->  {
//                    setCheckCardViews(RL_ADVISOR_TAX)
//                    selectedAdvisor = ADVISOR.ADVISOR_NAME_TAX
//                }
//                DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_HOME_INTE.wCode ->  {
//                    setCheckCardViews(RL_ADVISOR_HOME_INTE)
//                    selectedAdvisor = ADVISOR.ADVISOR_NAME_HOME_INTE
//                }
//                DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_CM.wCode -> {
//                    setCheckCardViews(RL_ADVISOR_CM)
//                    selectedAdvisor = ADVISOR.ADVISOR_NAME_CM
//                }
//                DiagSubStepFirstFragment.ADVISOR.ADVISOR_NAME_MANAGEMENT.wCode -> {
//                    setCheckCardViews(RL_ADVISOR_MANAGEMENT)
//                    selectedAdvisor = ADVISOR.ADVISOR_NAME_MANAGEMENT
//                }
//            }
//        }

        RL_ADVISOR_INVEST.setOnClickListener {
            return@setOnClickListener
            setCheckCardViews(it)
            selectedAdvisor = ADVISOR.ADVISOR_NAME_INVEST
        }
        RL_ADVISOR_MD.setOnClickListener {
//            mContext.showToast{"준비중입니다."}
            return@setOnClickListener
            setCheckCardViews(it)
            selectedAdvisor = ADVISOR.ADVISOR_NAME_MD
        }
        RL_ADVISOR_TAX.setOnClickListener {
//            mContext.showToast{"준비중입니다."}
            return@setOnClickListener
            setCheckCardViews(it)
            selectedAdvisor = ADVISOR.ADVISOR_NAME_TAX
        }
        RL_ADVISOR_HOME_INTE.setOnClickListener {
//            mContext.showToast{"준비중입니다."}
            return@setOnClickListener
            setCheckCardViews(it)
            selectedAdvisor = ADVISOR.ADVISOR_NAME_HOME_INTE
        }
        RL_ADVISOR_MANAGEMENT.setOnClickListener {
//            mContext.showToast{"준비중입니다."}
            return@setOnClickListener
            setCheckCardViews(it)
            selectedAdvisor = ADVISOR.ADVISOR_NAME_MANAGEMENT
        }
        RL_ADVISOR_CM.setOnClickListener {
//            mContext.showToast{"준비중입니다."}
            return@setOnClickListener
            setCheckCardViews(it)
            selectedAdvisor = ADVISOR.ADVISOR_NAME_CM
        }
    }

    private fun setCheckCardViews(view:View?){
        IV_ADVISOR_INVEST_CHECK.visibility = View.INVISIBLE
        IV_ADVISOR_MD_CHECK.visibility = View.INVISIBLE
        IV_ADVISOR_TAX_CHECK.visibility = View.INVISIBLE
        IV_ADVISOR_HOME_INTE_CHECK.visibility = View.INVISIBLE
        IV_ADVISOR_MANAGEMENT_CHECK.visibility = View.INVISIBLE
        IV_ADVISOR_CM_CHECK.visibility = View.INVISIBLE
        when (view){
            RL_ADVISOR_INVEST -> IV_ADVISOR_INVEST_CHECK.visibility = View.VISIBLE
            RL_ADVISOR_MD -> IV_ADVISOR_MD_CHECK.visibility = View.VISIBLE
            RL_ADVISOR_TAX -> IV_ADVISOR_TAX_CHECK.visibility = View.VISIBLE
            RL_ADVISOR_HOME_INTE -> IV_ADVISOR_HOME_INTE_CHECK.visibility = View.VISIBLE
            RL_ADVISOR_MANAGEMENT -> IV_ADVISOR_MANAGEMENT_CHECK.visibility = View.VISIBLE
            RL_ADVISOR_CM -> IV_ADVISOR_CM_CHECK.visibility = View.VISIBLE
        }
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

}