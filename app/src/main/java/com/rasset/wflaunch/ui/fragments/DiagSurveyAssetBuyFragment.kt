package com.rasset.wflaunch.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rasset.wflaunch.R
import com.rasset.wflaunch.model.DiagnoseAssetBuyInfo
import com.rasset.wflaunch.model.DiagnoseAssetSellInfo
import com.rasset.wflaunch.utils.isStrNullOrEmpty
import kotlinx.android.synthetic.main.fragment_diag_survey_asset_buy.*

/**
 * Created by devok on 2018-09-05.
 */

class DiagSurveyAssetBuyFragment : SurveyBaseFragment(){

    private object Holder { val INSTANCE = DiagSurveyAssetBuyFragment() }

    companion object {
        val singleTone: DiagSurveyAssetBuyFragment by lazy { Holder.INSTANCE }

        val instance: DiagSurveyAssetBuyFragment by lazy { DiagSurveyAssetBuyFragment() }

        fun newInstance(context: Context): Intent {
            val intent = Intent(context, DiagSurveyAssetBuyFragment::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_diag_survey_asset_buy, container, false)
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

    fun initFirst(){

    }

    override fun isValidDiagInputs(): Boolean {
        return true
    }

    override fun getDiagDatas(): DiagnoseAssetBuyInfo? {

        val estateType = RG_DIAG_RESTATE_TYPE.checkedItem?.tag.toString()
        val location = RG_BUY_LOCATION.checkedItem?.tag.toString()
        val buyTiming = RG_BUY_TIMING.checkedItem?.tag.toString()
        val investPurpose = RG_INVEST_PURPOSE.checkedItem?.tag.toString()
        val investScale = RG_INVEST_SCALE.checkedItem?.tag.toString()
        val investConsider = RG_INVEST_CONSIDER.checkedItem?.tag.toString()

        val cbBuyLocation = if (CB_BUY_CON_LOCATION.isChecked) 1L else 0
        val cbBuyDev = if (CB_BUY_CON_DEV.isChecked) 1L else 0
        val cbBuyMargin = if (CB_BUY_CON_MARGIN.isChecked) 1L else 0
        val cbBuyRevenue = if (CB_BUY_CON_REVENUE.isChecked) 1L else 0
        val cbBuyEtc = if (CB_BUY_CON_ETC.isChecked) 1L else 0

        if (estateType.isStrNullOrEmpty() || location.isStrNullOrEmpty() || buyTiming.isStrNullOrEmpty()
                || investPurpose.isStrNullOrEmpty() || investScale.isStrNullOrEmpty() || investConsider.isStrNullOrEmpty()
                || (cbBuyLocation == 0L && cbBuyDev == 0L && cbBuyMargin == 0L && cbBuyRevenue == 0L && cbBuyEtc == 0L)){
            return null
        }

        val diagInfo = DiagnoseAssetBuyInfo(buyItemType=estateType,buyArea=location,
                buyTime=buyTiming,investPurpose=investPurpose,cashAmount=investScale,considerations=investConsider
                ,consultPart11 = cbBuyLocation
                ,consultPart12 = cbBuyDev
                ,consultPart13 = cbBuyMargin
                ,consultPart14 = cbBuyRevenue
                ,consultPart15 = cbBuyEtc)
        diagInfo.diagnoseType = DiagSubStepFirstFragment.SURV_DIAGTYPE.SURV_TYPE_INVEST_BUY.diagType
        return diagInfo
    }


}