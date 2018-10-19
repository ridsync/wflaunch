package com.rasset.wflaunch.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.rasset.wflaunch.R
import com.rasset.wflaunch.network.OnNetworkListener
import com.rasset.wflaunch.network.protocol.ResultCode
import com.rasset.wflaunch.network.res.BaseModel
import com.rasset.wflaunch.ui.dialog.ProgressLockDialog
import com.rasset.wflaunch.utils.showToast




/**
 * Created by devok on 2018-09-03.
 */

open class BaseActivity : AppCompatActivity() , OnNetworkListener{

    val mContext : Context by lazy {
        applicationContext
    }
    lateinit var mLockDialog: ProgressLockDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLockDialog = ProgressLockDialog(this,R.style.TransparentDialog)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()

        mLockDialog.dismiss()
    }

    override fun onNetSuccess(data: BaseModel?, nReqType: Int) {
    }

    override fun onNetFail(retCode: Int, strErrorMsg: String, nReqType: Int) {
        if (isFinishing) return
        doAlertCommonNetFail(retCode,strErrorMsg,nReqType)
    }

    override fun onProgresStart(nReqType: Int) {
    }

    override fun onProgresStop(nReqType: Int) {
    }

    fun doAlertCommonNetFail(retCode: Int, strErrorMsg: String, reqType: Int) {
        if (isFinishing) return

        if (retCode == ResultCode.API_AUTH_NOT_EXIST_USER) {
            showToast {
                getString(R.string.popup_alert_not_exist_user_ask_login)
            }
        } else if (retCode == ResultCode.API_AUTH_NOT_EXIST_OTHERUSER) {
            showToast {
                getString(R.string.popup_alert_not_exist_user_ask_login)
            }
        } else {
            showToast { "서버통신실패 : ${strErrorMsg}" }
        }
    }

}
