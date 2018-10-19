package com.rasset.wflaunch.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rasset.wflaunch.R
import com.rasset.wflaunch.model.DiagnoseTaxFarmInfo
import com.rasset.wflaunch.utils.isStrNullOrEmpty
import kotlinx.android.synthetic.main.fragment_diag_survey_tax_farmland.*

/**
 * Created by devok on 2018-09-05.
 */

class DiagSurveyTaxFarmLandFragment : SurveyBaseFragment() {

    private object Holder { val INSTANCE = DiagSurveyTaxFarmLandFragment() }

    companion object {
        val singleTone: DiagSurveyTaxFarmLandFragment by lazy { Holder.INSTANCE }

        val instance: DiagSurveyTaxFarmLandFragment by lazy { DiagSurveyTaxFarmLandFragment() }

        fun newInstance(context: Context): Intent {
            val intent = Intent(context, DiagSurveyTaxFarmLandFragment::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_diag_survey_tax_farmland, container, false)
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

    override fun getDiagDatas(): DiagnoseTaxFarmInfo? {

        val usage = RG_USAGE.checkedItem?.tag.toString()
        val isFarmLand = RG_FARMLAND.checkedItem?.tag.toString()
        val isPurpose = RG_USE_PURPOSE.checkedItem?.tag.toString()
        val exceptLand = RG_EXECPT_FARMLAND.checkedItem?.tag.toString()
        val residence = RG_RESIDENCE.checkedItem?.tag.toString()
        val urban = RG_URBAN.checkedItem?.tag.toString()
        val needConsult = RG_NEED_CONSULT.checkedItem?.tag.toString()

        if (usage.isStrNullOrEmpty() || isFarmLand.isStrNullOrEmpty() || isPurpose.isStrNullOrEmpty()
                || exceptLand.isStrNullOrEmpty() || residence.isStrNullOrEmpty() || urban.isStrNullOrEmpty() || needConsult.isStrNullOrEmpty()){
            return null
        }

        val diagInfo = DiagnoseTaxFarmInfo(farmRealType=usage,
                farmUnchangeType=isFarmLand,
                farmPurposeType = isPurpose,
                farmExcludeTye = exceptLand,
                farmPeriodType = residence,
                farmTransType = urban,
                farmConsultYn = needConsult
        )
        diagInfo.diagnoseType = DiagSubStepFirstFragment.SURV_DIAGTYPE.SURV_TYPE_TAX_FALM.diagType
        return diagInfo
    }

}