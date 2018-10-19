package com.rasset.wflaunch.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.rasset.wflaunch.R
import com.rasset.wflaunch.core.AppConst
import com.rasset.wflaunch.model.DiagnoseAssetSellInfo
import com.rasset.wflaunch.model.DiagnoseCMInfo
import com.rasset.wflaunch.model.DiagnoseInfo
import com.rasset.wflaunch.ui.dialog.BaseDialogFragment
import com.rasset.wflaunch.ui.dialog.SearchAddressDialog
import com.rasset.wflaunch.utils.hideIME
import com.rasset.wflaunch.utils.isStrNullOrEmpty
import kotlinx.android.synthetic.main.fragment_diag_survey_cm.*

/**
 * Created by devok on 2018-09-05.
 */

class DiagSurveyCMFragment : SurveyBaseFragment() {

    private object Holder { val INSTANCE = DiagSurveyCMFragment() }

    companion object {
        val singleTone: DiagSurveyCMFragment by lazy { Holder.INSTANCE }

        val instance: DiagSurveyCMFragment by lazy { DiagSurveyCMFragment() }

        fun newInstance(context: Context): Intent {
            val intent = Intent(context, DiagSurveyCMFragment::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_diag_survey_cm, container, false)
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
        BTN_SEARCH_ADDRESS_SHOW.setOnClickListener {
            showDialog()
        }

        ET_LOCATION_ADDRESS_DETAIL.setOnEditorActionListener { v, actionId, event ->
            if (v?.id === ET_LOCATION_ADDRESS_DETAIL.id && actionId === EditorInfo.IME_ACTION_NEXT) {
                hideIME(mContext,ET_LOCATION_ADDRESS_DETAIL)
            }
            false
        }
    }

    private fun showDialog(){

        val dialog = SearchAddressDialog.newInstance(mContext).apply {
            setOnDismissListener(object : BaseDialogFragment.OnDismissListener{
                override fun onDismiss(dialog: BaseDialogFragment) {
                    val result = dialog as SearchAddressDialog
                    result.mSelectedAddress?.let {
                        this@DiagSurveyCMFragment.TV_LOCATION_ADDRESS.text = result.mSelectedAddress
                    }
                }
            })
        }
        dialog.show(fragmentManager, AppConst.DIALOG_CUSTOMER_INFO_PRIVACY)
    }

    override fun isValidDiagInputs(): Boolean {
        return true
    }

    override fun getDiagDatas(): DiagnoseCMInfo? {

        val estateField = RG_CONSULT_FIELD.checkedItem?.tag.toString()
        val etcFieldContent = ET_CONSULT_FIELD_ETC.text.toString()
        val address = TV_LOCATION_ADDRESS.text.toString().trim()
        val addressDetail = ET_LOCATION_ADDRESS_DETAIL.text.toString().trim()
        val construct = RG_DIAG_CONSTRUCT.checkedItem?.tag.toString()
        val purpose = RG_PURPOSE.checkedItem?.tag.toString()
        val purposeEtc = ET_PURPOSE_ETC.text.toString().trim()
        val totalCost = RG_DIAG_TOTAL_COST.checkedItem?.tag.toString()
        val diagConsider = RG_DIAG_CONSIDER.checkedItem?.tag.toString()
        val needConsult = RG_NEED_CONSULT.checkedItem?.tag.toString()

        if (estateField.isStrNullOrEmpty() || address.isStrNullOrEmpty() || construct.isStrNullOrEmpty()
                || purpose.isStrNullOrEmpty() || totalCost.isStrNullOrEmpty() || diagConsider.isStrNullOrEmpty()
                || needConsult.isStrNullOrEmpty()){
            return null
        }

        return DiagnoseCMInfo(itemType=estateField,
                address="$address $addressDetail",
                constructType=construct,
                purposeType=purpose,
                expenseType=totalCost,
                troubleType=diagConsider,
                consultYn=needConsult.toLong(),
                consultEtc01=etcFieldContent,
                consultEtc02=purposeEtc
                )
    }
}