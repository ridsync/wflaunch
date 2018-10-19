package com.rasset.wflaunch.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rasset.wflaunch.R;
import com.rasset.wflaunch.ui.components.CustomWebView;

/**
 * Created by andman on 2016-01-12.
 */
public class SearchAddressDialog extends BaseDialogFragment   implements View.OnClickListener{

    private static volatile SearchAddressDialog sFag;

    public interface OnPositvelListener {
        void onClickPositive(SearchAddressDialog dialog);
    }
    public interface OnNegativelListener {
        void onClickNegative(SearchAddressDialog dialog);
    }

    public static SearchAddressDialog newInstance(Context context) {
        SearchAddressDialog mFag = new SearchAddressDialog();
        mFag.mContext = context;
        return mFag;
    }

    public SearchAddressDialog() {
    }

    public String mSelectedAddress;
    int mResIdpositive = -1;
    int mResIdNegative = -1;
    OnPositvelListener positiveListener ;
    OnNegativelListener negativeListener ;

    CustomWebView mWebView;
    TextView mResultView;
    Handler mHandler;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.TransparentDialog);
        mRootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_search_address, null);

//        builder.setTitle(mResTitle == 0 ? R.string.common_alert : mResTitle);
        mHandler = new Handler();

        // 버튼
        mWebView = (mRootView.findViewById(R.id.WV_SEARCH_ADDRESS));
        mResultView = (mRootView.findViewById(R.id.TV_SELECTED_ADDRESS));
        mBtnNegative = (mRootView.findViewById(R.id.BTN_CANCEL));
        mBtnNegative.setOnClickListener(this);
        if(mResIdNegative !=-1) {
            mBtnNegative.setVisibility(View.VISIBLE);
            mBtnNegative.setText(mResIdNegative);
        }
        mBtnPositive = (mRootView.findViewById(R.id.BTN_DONE));
        if(mResIdpositive !=-1) {
            mBtnPositive.setVisibility(View.VISIBLE);
            mBtnPositive.setText(mResIdpositive);
        }
        mBtnPositive.setOnClickListener(this);

        initWebView();
        builder.setView(mRootView);
        Dialog dlg = builder.create();
        return dlg;
    }

    private void initWebView(){
        // JavaScript 허용
        mWebView.setWebViewDefaultSetting();
        mWebView.addJavascriptInterface(new AndroidBridge(), "smhsTabApp");
        // webview url load
        mWebView.loadUrl("http://lyrevega.dothome.co.kr/address.php");
    }

    private class AndroidBridge {
        @JavascriptInterface // 도로명
        public void setAddressR(final String zonecode, final String roadAddress, final String buildingName) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (buildingName!=null && buildingName.length() > 0){
                        String address = String.format("%s (%s)", roadAddress, buildingName);
                        mSelectedAddress = address;
                    } else {
                        String address = String.format("%s", roadAddress);
                        mSelectedAddress = address;
                    }
//                    mResultView.setText(mSelectedAddress);
                    dismiss();
                }
            });
        }
        @JavascriptInterface // 지번
        public void setAddressJ(final String zonecode, final String roadAddress, final String buildingName) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String address = String.format("%s", roadAddress);
                    mSelectedAddress = address;
//                    mResultView.setText(mSelectedAddress);
                    dismiss();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.BTN_CANCEL){
            if (negativeListener !=null)
                negativeListener.onClickNegative(this);
        } else if (v.getId() == R.id.BTN_DONE) {
            if (positiveListener !=null)
                positiveListener.onClickPositive(this);
        }
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTvContents = null;
    }

    public void setPositiveButton(int mResIdpositive, OnPositvelListener listener) {
        this.mResIdpositive = mResIdpositive;
        this.positiveListener = listener;
    }

    public void setNegativeButton(int mResIdNegative, OnNegativelListener listener) {
        this.mResIdNegative = mResIdNegative;
        this.negativeListener = listener;
    }
}

