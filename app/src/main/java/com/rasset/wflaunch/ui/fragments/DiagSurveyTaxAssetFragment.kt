package com.rasset.wflaunch.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rasset.wflaunch.R
import com.rasset.wflaunch.model.DiagnoseTaxAssetInfo
import com.rasset.wflaunch.utils.isStrNullOrEmpty
import kotlinx.android.synthetic.main.fragment_diag_survey_asset_buy.*
import kotlinx.android.synthetic.main.fragment_diag_survey_tax_asset.*

/**
 * Created by devok on 2018-09-05.
 */

class DiagSurveyTaxAssetFragment : SurveyBaseFragment() {

    private object Holder { val INSTANCE = DiagSurveyTaxAssetFragment() }

    companion object {
        val singleTone: DiagSurveyTaxAssetFragment by lazy { Holder.INSTANCE }

        val instance: DiagSurveyTaxAssetFragment by lazy { DiagSurveyTaxAssetFragment() }

        fun newInstance(context: Context): Intent {
            val intent = Intent(context, DiagSurveyTaxAssetFragment::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_diag_survey_tax_asset, container, false)
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

    override fun getDiagDatas(): DiagnoseTaxAssetInfo?{

        val taxField = RG_DIAG_TAX_FIELD.checkedItem?.tag.toString()
        val housing = RG_DIAG_SELL_HOUSING.checkedItem?.tag.toString()
        val controlLocation = RG_CONTROL.checkedItem?.tag.toString()
        val ownPeriod = RG_OWN_PERIOD.checkedItem?.tag.toString()
        val higherPrice = RG_HIGHER_PRICE.checkedItem?.tag.toString()

        val ownRassetStore = if (RB_OWN_RASSET_STORE.isChecked) 1L else 0
        val ownRassetParcel = if (RB_OWN_RASSET_PARCEL.isChecked) 1L else 0
        val ownRassetResidence = if (RB_OWN_RASSET_RESIDENCE.isChecked) 1L else 0
        val ownRassetOfficetel = if (RB_OWN_RASSET_OFFICETEL.isChecked) 1L else 0
        val ownRassetLand = if (RB_OWN_RASSET_LAND.isChecked) 1L else 0
        val ownRassetEtc = if (RB_OWN_RASSET_ETC.isChecked) 1L else 0
        val ownRassetEtcContents = ET_OWN_RASSET_ETC.text.toString().trim()

        val needConsult = RG_NEED_CONSULT.checkedItem?.tag.toString()

        if (taxField.isStrNullOrEmpty() || housing.isStrNullOrEmpty() || controlLocation.isStrNullOrEmpty()
                || ownPeriod.isStrNullOrEmpty() || higherPrice.isStrNullOrEmpty() || needConsult.isStrNullOrEmpty()
                || (ownRassetStore == 0L && ownRassetParcel == 0L && ownRassetResidence == 0L && ownRassetOfficetel == 0L
                        && ownRassetLand == 0L && ownRassetEtc == 0L)){
            return null
        }

        val diagInfo = DiagnoseTaxAssetInfo(itemType=taxField,
                ownerHouse = housing,
                areaType = controlLocation,
                holdType = ownPeriod,
                priceType = higherPrice,
                consultPart01 = ownRassetStore,
                consultPart02 = ownRassetParcel,
                consultPart03 = ownRassetResidence,
                consultPart04 = ownRassetOfficetel,
                consultPart05 = ownRassetLand,
                consultPart06 = ownRassetEtc,
                consultEtc01 = ownRassetEtcContents,
                consultYn = needConsult.toLong()
                )
        diagInfo.diagnoseType = DiagSubStepFirstFragment.SURV_DIAGTYPE.SURV_TYPE_TAX_ASSET.diagType
        return diagInfo
    }

}