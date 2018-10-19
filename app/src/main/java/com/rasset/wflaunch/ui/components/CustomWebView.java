package com.rasset.wflaunch.ui.components;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by devok on 2018-09-20.
 */
public class CustomWebView extends WebView
{
    Context mContext;

    public CustomWebView(Context context) {
        super(context, null);
        mContext = context;
    }

    public CustomWebView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomWebView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }

    @Override
    public boolean onCheckIsTextEditor()
    {
        return true;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public boolean setWebViewDefaultSetting()
    {
        setScrollBarStyle(SCROLLBARS_INSIDE_OVERLAY);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus(View.FOCUS_DOWN);

        WebSettings webSettings = getSettings();
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(false);
        webSettings.setUseWideViewPort(false);
        setWebChromeClient(new WebChromeClient());

        setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus())
                        {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });

        return true;
    }
}