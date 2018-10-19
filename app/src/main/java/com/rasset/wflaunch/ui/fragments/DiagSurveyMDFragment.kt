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
import com.rasset.wflaunch.model.DiagnoseMDInfo
import com.rasset.wflaunch.ui.dialog.BaseDialogFragment
import com.rasset.wflaunch.ui.dialog.SearchAddressDialog
import com.rasset.wflaunch.utils.hideIME
import com.rasset.wflaunch.utils.isStrNullOrEmpty
import kotlinx.android.synthetic.main.fragment_diag_survey_md.*

/**
 * Created by devok on 2018-09-05.
 */

class DiagSurveyMDFragment : SurveyBaseFragment() {

    private object Holder { val INSTANCE = DiagSurveyMDFragment() }

    companion object {
        val singleTone: DiagSurveyMDFragment by lazy { Holder.INSTANCE }

        val instance: DiagSurveyMDFragment by lazy { DiagSurveyMDFragment() }

        fun newInstance(context: Context): Intent {
            val intent = Intent(context, DiagSurveyMDFragment::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_diag_survey_md, container, false)
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
                        this@DiagSurveyMDFragment.TV_LOCATION_ADDRESS.text = result.mSelectedAddress
                    }
                }
            })
        }
        dialog.show(fragmentManager, AppConst.DIALOG_CUSTOMER_INFO_PRIVACY)
    }

    override fun isValidDiagInputs(): Boolean {
        return true
    }

    override fun getDiagDatas(): DiagnoseMDInfo? {

        val address = TV_LOCATION_ADDRESS.text.toString().trim()
        val addressDetail = ET_LOCATION_ADDRESS_DETAIL.text.toString().trim()

        val investPurpose = RG_DIAG_MD_PURPOSE.checkedItem?.tag.toString()
        val sellWonder = RG_DIAG_SELL_WONDER.checkedItem?.tag.toString()
        val investScale = RG_INVEST_SCALE.checkedItem?.tag.toString()
        val sellhasExp = RG_SELL_HASEXP.checkedItem?.tag.toString()
        val buyMotive = RG_DIAG_MD_MOTIVE.checkedItem?.tag.toString()
        val needConsult = RG_SELL_NEED_CONSULTANT.checkedItem?.tag.toString()

        if (address.isStrNullOrEmpty() || investPurpose.isStrNullOrEmpty() || sellWonder.isStrNullOrEmpty()
                || investScale.isStrNullOrEmpty()  || sellhasExp.isStrNullOrEmpty() || buyMotive.isStrNullOrEmpty() || needConsult.isStrNullOrEmpty()){
            return null
        }

        return DiagnoseMDInfo(address="$address $addressDetail"
                ,mdReason=investPurpose
                ,mdQuestion=sellWonder
                ,mdBuyCash=investScale
                ,mdExperienced=sellhasExp
                ,mdPurchase=buyMotive
                ,consultYn=needConsult)
    }

}