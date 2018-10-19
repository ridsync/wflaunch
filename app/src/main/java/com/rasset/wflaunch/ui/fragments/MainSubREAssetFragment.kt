package com.rasset.wflaunch.ui.fragments

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.rasset.wflaunch.R
import com.rasset.wflaunch.network.res.BaseModel
import kotlinx.android.synthetic.main.fragment_main_re_asset.*

/**
 * Created by devok on 2018-09-05.
 */

class MainSubREAssetFragment : BaseFragment() {

    private object Holder { val INSTANCE = MainSubREAssetFragment() }

    companion object {
        val singleTone: MainSubREAssetFragment by lazy { Holder.INSTANCE }

        val instance: MainSubREAssetFragment by lazy { MainSubREAssetFragment() }

        fun newInstance(context: Context): Intent {
            val intent = Intent(context, MainSubREAssetFragment::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater?.inflate(R.layout.fragment_main_re_asset, container, false)
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
        WV_REASSET.run {
            webViewClient = object :  WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    Log.d("webView", "onPageStarted $url")
                }
                override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    Log.d("webView", "onReceivedHttpError ${errorResponse.toString()}")
                }
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    request?.let {
                        view?.loadUrl(it.url.toString())
                        Log.d("webView", "shouldOverrideUrlLoading loadUrl ${it.url}")
                    }
                    Log.d("webView", "shouldOverrideUrlLoading $request")
                    return false
                }
                override fun onLoadResource(view: WebView?, url: String?) {
                    super.onLoadResource(view, url)
//                Log.d("webView", "onLoadResource $url")
                }

                override fun onPageFinished(view: WebView, url: String) {
                    Log.d("webView", "onPageFinished $url")
                }

            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                }
            }
            clearCache(true)
            clearHistory()
            setNetworkAvailable(true)
            settings.javaScriptEnabled = true
//        settings.javaScriptCanOpenWindowsAutomatically = true
            settings.domStorageEnabled = true
            settings.userAgentString = settings.userAgentString
            Log.d("webView ", "userAgentString - ${settings.userAgentString}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setBackgroundColor(Color.TRANSPARENT)
            }
            loadUrl("http://www.smarthaus.co.kr/account/rich/survey/join")
        }

    }

    override fun onNetSuccess(data: BaseModel?, nReqType: Int) {

    }

    override fun onNetFail(retCode: Int, strErrorMsg: String, nReqType: Int) {
        super.onNetFail(retCode, strErrorMsg, nReqType)
    }

    override fun onProgresStart(nReqType: Int) {

    }

    override fun onProgresStop(nReqType: Int) {

    }

}