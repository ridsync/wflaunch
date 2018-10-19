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
import com.rasset.wflaunch.model.DiagnoseHomeInteInfo
import com.rasset.wflaunch.model.DiagnoseInfo
import com.rasset.wflaunch.ui.dialog.BaseDialogFragment
import com.rasset.wflaunch.ui.dialog.SearchAddressDialog
import com.rasset.wflaunch.utils.hideIME
import com.rasset.wflaunch.utils.isStrNullOrEmpty
import kotlinx.android.synthetic.main.fragment_diag_survey_home_inte.*

/**
 * Created by devok on 2018-09-05.
 */

class DiagSurveyHomeInteFragment : SurveyBaseFragment() {

    private object Holder { val INSTANCE = DiagSurveyHomeInteFragment() }

    companion object {
        val singleTone: DiagSurveyHomeInteFragment by lazy { Holder.INSTANCE }

        val instance: DiagSurveyHomeInteFragment by lazy { DiagSurveyHomeInteFragment() }

        fun newInstance(context: Context): Intent {
            val intent = Intent(context, DiagSurveyHomeInteFragment::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_diag_survey_home_inte, container, false)
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


    override fun isValidDiagInputs(): Boolean {
        return true
    }

    private fun showDialog(){

        val dialog = SearchAddressDialog.newInstance(mContext).apply {
            setOnDismissListener(object : BaseDialogFragment.OnDismissListener{
                override fun onDismiss(dialog: BaseDialogFragment) {
                    val result = dialog as SearchAddressDialog
                    result.mSelectedAddress?.let {
                        this@DiagSurveyHomeInteFragment.TV_LOCATION_ADDRESS.text = result.mSelectedAddress
                    }
                }
            })
        }
        dialog.show(fragmentManager, AppConst.DIALOG_CUSTOMER_INFO_PRIVACY)
    }

    override fun getDiagDatas(): DiagnoseHomeInteInfo? {


        val address = TV_LOCATION_ADDRESS.text.toString().trim()
        val addressDetail = ET_LOCATION_ADDRESS_DETAIL.text.toString().trim()

        val diagArea = RG_DIAG_AREA.checkedItem?.tag.toString()
        val totalCost = RG_TOTAL_COST.checkedItem?.tag.toString()
        val style = RG_STYLE.checkedItem?.tag.toString()
        val date = RG_CONSTRUCT_DATE.checkedItem?.tag.toString()
        val needConsult = RG_INVEST_CONSIDER.checkedItem?.tag.toString()

        if (address.isStrNullOrEmpty() || diagArea.isStrNullOrEmpty() || totalCost.isStrNullOrEmpty()
                || style.isStrNullOrEmpty() || date.isStrNullOrEmpty() || needConsult.isStrNullOrEmpty()){
            return null
        }

        return DiagnoseHomeInteInfo(address="$address $addressDetail",
                sizeType=diagArea,
                costType=totalCost,
                styleType=style,
                buildType=date,
                consultYn=needConsult.toLong()
                )
    }
}