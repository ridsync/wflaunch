package com.rasset.wflaunch.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.rasset.wflaunch.R
import com.rasset.wflaunch.core.AppConst
import com.rasset.wflaunch.model.DiagnoseAssetSellInfo
import com.rasset.wflaunch.ui.dialog.BaseDialogFragment
import com.rasset.wflaunch.ui.dialog.SearchAddressDialog
import com.rasset.wflaunch.utils.hideIME
import com.rasset.wflaunch.utils.isStrNullOrEmpty
import kotlinx.android.synthetic.main.fragment_diag_survey_asset_sell.*
import java.util.*

/**
 * Created by devok on 2018-09-05.
 */

class DiagSurveyAssetSellFragment : SurveyBaseFragment() {

    private object Holder { val INSTANCE = DiagSurveyAssetSellFragment() }

    companion object {
        val singleTone: DiagSurveyAssetSellFragment by lazy { Holder.INSTANCE }

        val instance: DiagSurveyAssetSellFragment by lazy { DiagSurveyAssetSellFragment() }

        fun newInstance(context: Context): Intent {
            val intent = Intent(context, DiagSurveyAssetSellFragment::class.java)
            return intent
        }
    }

    var mPurchasedYear:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_diag_survey_asset_sell, container, false)
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

        ET_LOCATION_ADDRESS_DETAIL.setOnEditorActionListener { v, actionId, event ->
            if (v?.id === ET_LOCATION_ADDRESS_DETAIL.id && actionId === EditorInfo.IME_ACTION_NEXT) {
                hideIME(mContext,ET_LOCATION_ADDRESS_DETAIL)
            }
            false
        }

        BTN_SEARCH_ADDRESS_SHOW.setOnClickListener {
            showDialog()
        }

        initSpinners()
    }

    private fun showDialog(){

        val dialog = SearchAddressDialog.newInstance(mContext).apply {
            setOnDismissListener(object : BaseDialogFragment.OnDismissListener{
                override fun onDismiss(dialog: BaseDialogFragment) {
                    val result = dialog as SearchAddressDialog
                    result.mSelectedAddress?.let {
                        this@DiagSurveyAssetSellFragment.TV_LOCATION_ADDRESS.text = result.mSelectedAddress
                    }
                }
            })
        }
        dialog.show(fragmentManager, AppConst.DIALOG_CUSTOMER_INFO_PRIVACY)
    }

    private fun initSpinners() {

        /* 스피너 디폴트 텍스트 고민좀 해보자 */
        // ++ BORN ++
        val maxAge = 50
        val minAge = 0
        val cal = Calendar.getInstance()
        val nowYear = cal.get(Calendar.YEAR)
        val minYear = nowYear - minAge
        val rangeAge = maxAge - minAge

        val bornSpinnerAdapter = object : ArrayAdapter<String>(mContext, R.layout.item_spinner) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val row = inflater.inflate(R.layout.item_spinner, parent, false)
                val label = row.findViewById(android.R.id.text1) as TextView
//                label.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
                if (position == 0) {
                    label.text = "선택"
//                    label.setTextColor(resources.getColor(R.color.color_black_12))
                } else if (position < count) {
//                    label.setTextColor(resources.getColor(R.color.color_black_87))
                    label.text = getItem(position) + "년"
                }

                return row
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
                var v: View? = null
                if (position == 0) {
                    val tv = TextView(context)
                    tv.height = 0
                    tv.visibility = View.GONE
                    v = tv
                } else {
                    v = super.getDropDownView(position, null, parent)
                }
                parent.isVerticalScrollBarEnabled = false
                return v
            }
        }

        val arrYear = arrayOfNulls<String>(rangeAge)
        for (i in 0 until rangeAge) {
            arrYear[i] = (minYear - i).toString() + ""
        }

        // index 0
        bornSpinnerAdapter.add("년도선택")
        for (i in arrYear.indices) {
            bornSpinnerAdapter.add(arrYear[i])
        }

        bornSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SPN_CONSULTING_DATE_YMD.adapter = bornSpinnerAdapter
        SPN_CONSULTING_DATE_YMD.onItemSelectedListener = mItemSelectedListener
    }

    private val mItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            if (position == 0) return
            mPurchasedYear = parent.getItemAtPosition(position).toString()
        }

        override fun onNothingSelected(parent: AdapterView<*>) {

        }
    }

    override fun isValidDiagInputs(): Boolean {
        return true
    }

    override fun getDiagDatas(): DiagnoseAssetSellInfo? {

        val estateType = RG_DIAG_RESTATE_TYPE.checkedItem?.tag.toString()
        val address = TV_LOCATION_ADDRESS.text.toString().trim()
        val addressDetail = ET_LOCATION_ADDRESS_DETAIL.text.toString().trim()
        mPurchasedYear
        val sellPurpose = RG_DIAG_SELL_PURPOSE.checkedItem?.tag.toString()
        val lowerPrice = ET_SELL_LOWER.text.toString().trim()
        val higherPrice = ET_SELL_HIGHER.text.toString().trim()
        val sellTiming = RG_SELL_TIMING.checkedItem?.tag.toString()
        val cbSellTiming = if (CB_SELL_CON_TIMING.isChecked) 1L else 0
        val cbSellPrice = if (CB_SELL_CON_PRICE.isChecked) 1L else 0
        val cbSellRequest = if (CB_SELL_CON_REQUEST.isChecked) 1L else 0
        val cbSellMethod = if (CB_SELL_CON_METHOD.isChecked) 1L else 0
        val cbSellEtc = if (CB_SELL_CON_ETC.isChecked) 1L else 0

        if (estateType.isStrNullOrEmpty() || address.isStrNullOrEmpty() || sellPurpose.isStrNullOrEmpty()
         || lowerPrice.isStrNullOrEmpty() || higherPrice.isStrNullOrEmpty() || sellTiming.isStrNullOrEmpty()
                || (cbSellTiming == 0L && cbSellPrice == 0L && cbSellRequest == 0L && cbSellMethod == 0L && cbSellEtc == 0L)){
            return null
        }
        val diagInfo = DiagnoseAssetSellInfo(itemType=estateType,address="$address $addressDetail",buyYear=mPurchasedYear,
                sellPurpose = sellPurpose.toLong(),minAmount = lowerPrice,maxAmount = higherPrice,sellTime = sellTiming
                ,consultPart01 = cbSellTiming
                ,consultPart02 = cbSellPrice
                ,consultPart03 = cbSellRequest
                ,consultPart04 = cbSellMethod
                ,consultPart05 = cbSellEtc)
        diagInfo.diagnoseType = DiagSubStepFirstFragment.SURV_DIAGTYPE.SURV_TYPE_INVEST_SELL.diagType
        return diagInfo
    }

}