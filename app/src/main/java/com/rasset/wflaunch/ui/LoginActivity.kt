package com.rasset.wflaunch.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.rasset.wflaunch.R
import com.rasset.wflaunch.core.AppConst
import com.rasset.wflaunch.core.TabApp

import com.rasset.wflaunch.network.NetManager
import com.rasset.wflaunch.network.protocol.ParamKey
import com.rasset.wflaunch.network.protocol.ReqType
import com.rasset.wflaunch.network.protocol.ResultCode
import com.rasset.wflaunch.network.res.BaseModel
import com.rasset.wflaunch.network.res.ResUserLogin
import com.rasset.wflaunch.network.task.MainListTask
import com.rasset.wflaunch.ui.dialog.MainCustomDialog
import com.rasset.wflaunch.utils.JUtil.isDoubleClick
import com.rasset.wflaunch.utils.Logger
import com.rasset.wflaunch.utils.Prefer
import com.rasset.wflaunch.utils.showToast
import kotlinx.android.synthetic.main.activity_login.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [testDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class LoginActivity : BaseActivity() {
    companion object {

        private val INTENT_USER_ID = "user_id"
        private val instance: LoginActivity? = null
        private var count = 0

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
//            intent.putExtra(INTENT_USER_ID, user.id)
            return intent
        }
    }

    lateinit var mGoogleSignInClient: GoogleSignInClient
    private var account: GoogleSignInAccount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        BTN_LOGIN.setOnClickListener {

            val userId = ET_LOGIN_USERNAME.text.toString().trim()
            val password = ET_LOGIN_PASSWORD.text.toString().trim()

            if (userId.isEmpty()){
                showToast { "[ 아이디를 입력해주세요 ]" }
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                showToast { "[ 패스워드를 입력해주세요 ]" }
                return@setOnClickListener
            }
            reqNetLogin(userId,password)
        }

        tvEasterEgg.setOnClickListener {
            if(count >= 7){
                showToast { "Good Luck !!" }
            }
            count++
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestProfile()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        BTN_GOOGLE.setOnClickListener {

            val signInIntent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, 101)
        }

//        ET_LOGIN_USERNAME.addTextChangedListener(mIdWatcher)
//        ET_LOGIN_PASSWORD.addTextChangedListener(mPassWatcher)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 101) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
//                account = task.getResult(ApiException::class.java)
//                if (account != null && account?.getAccount() != null) {
//                    Logger.d("ACCOUNT", " " + account?.getDisplayName() + " / " + account?.getEmail())
//                }
                // TODO 유저 임시저장
                Prefer.setSharedPreference(AppConst.PREFERENCE_USERINFO_ID,1010220L,mContext)
                startActivity(MainActivity.newIntent(mContext))
                finish()
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
                showToast {
                    "구글 로그인 성공"
                }
            } catch (e: ApiException) {
                Logger.w("Login", "signInResult:failed code=" + e.statusCode)
//                AlertToast.show(mContext, "계정정보 가져오기 실패함. 재시도 필요", Toast.LENGTH_SHORT)
            }

        }
    }

    private fun showDialog(){

        val dialog = MainCustomDialog.newInstance(mContext).apply {
            setTitle(R.string.common_alert)
            setMsgContents("아이디 정보가 맞지 않습니다.\n본사 담당자에게 문의 해주세요.")
            setPositiveButton(R.string.common_confirm, MainCustomDialog.OnPositvelListener { dialog ->
                if (isDoubleClick(dialog.view)) return@OnPositvelListener
//                    showToast { "감사합니다" }
                }
            )
        }
        dialog.show(supportFragmentManager,AppConst.DIALOG_LOGIN_FAIL)
    }


    private fun reqNetLogin(userId: String, password: String) {
        val task = MainListTask(applicationContext, ReqType.REQUEST_TYPE_POST_USER_LOGIN, this)
        task.addParam(ParamKey.PARAM_LOGIN_ID, userId)
        task.addParam(ParamKey.PARAM_LOGIN_PASSWORD, password)
        NetManager.startTask(task)
    }

    override fun onNetSuccess(data: BaseModel?, nReqType: Int) {
        Logger.d("onNetSuccess  ")

        if (data is ResUserLogin){
            showToast { "로그인 성공 : OK" }
            data.userInfo?.let {
                TabApp.userInfo = it
            }
            startActivity(MainActivity.newIntent(mContext))
            finish()
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        }
    }

    override fun onNetFail(retCode: Int, strErrorMsg: String, nReqType: Int) {
        Logger.d("onNetFail  ")
        if (nReqType == ReqType.REQUEST_TYPE_POST_USER_LOGIN) {
            // 로그인 실패
            if (retCode != ResultCode.API_AUTH_NOT_EXIST_USER) {
                showDialog()
                return
            }
        }
        super.onNetFail(retCode,strErrorMsg,nReqType)
    }

    override fun onProgresStart(nReqType: Int) {
        Logger.d("onProgresStart  ")
        mLockDialog.show()
    }

    override fun onProgresStop(nReqType: Int) {
        Logger.d("onProgresStop  ")

        Handler().postDelayed({
                mLockDialog.cancel()
        },2000)
    }

    private val mIdWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) {
//            BTN_LOGIN.isEnabled = s.length >= 0
        }
    }
    private val mPassWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) {
        }
    }

}
