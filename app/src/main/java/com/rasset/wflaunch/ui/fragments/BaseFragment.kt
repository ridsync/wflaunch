package com.rasset.wflaunch.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import com.rasset.wflaunch.R
import com.rasset.wflaunch.network.OnNetworkListener
import com.rasset.wflaunch.network.res.BaseModel
import com.rasset.wflaunch.ui.BaseActivity
import com.rasset.wflaunch.ui.dialog.ProgressLockDialog

/**
 * Created by devok on 2018-09-03.
 */

open class BaseFragment : Fragment() , OnNetworkListener{

    lateinit var mLockDialog: ProgressLockDialog
    lateinit var mActivity: FragmentActivity
    var mRootView: View? = null
    lateinit var mContext: Context
    var isFirstInit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            mActivity = it
            mContext = mActivity
            mLockDialog = ProgressLockDialog(mActivity, R.style.TransparentDialog)
        }
    }

    override fun onStop() {
        super.onStop()
            mLockDialog.dismiss()
    }

    override fun onNetSuccess(data: BaseModel?, nReqType: Int) {

    }

    override fun onNetFail(retCode: Int, strErrorMsg: String, nReqType: Int) {
        if (mActivity is BaseActivity){
            (mActivity as BaseActivity).doAlertCommonNetFail(retCode, strErrorMsg, nReqType)
        }
    }

    override fun onProgresStart(nReqType: Int) {

    }

    override fun onProgresStop(nReqType: Int) {

    }

}
