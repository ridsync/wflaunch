package com.rasset.wflaunch.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.rasset.wflaunch.R;

/**
 * Created by andman on 2016-01-12.
 */
public class MainCustomDialog extends BaseDialogFragment   implements View.OnClickListener{

    private static volatile MainCustomDialog sFag;

    public interface OnPositvelListener {
        void onClickPositive(MainCustomDialog dialog);
    }
    public interface OnNegativelListener {
        void onClickNegative(MainCustomDialog dialog);
    }

    public static MainCustomDialog newInstance(Context context) {
        MainCustomDialog mFag = new MainCustomDialog();
        mFag.mContext = context;
        return mFag;
    }

    public MainCustomDialog() {
    }

    int mResContentLayout = -1;
    int mResTitle;
    SpannableString mSpanContents;
    String mStrContents;
    Button mBtnAloneDone = null;
    int mResIdAloneDone = -1;
    int mResIdpositive = -1;
    int mResIdNegative = -1;
    boolean isCancelable = true;
    boolean isCanceledOnTouchOutside = false;
    OnPositvelListener positiveListener ;
    OnNegativelListener negativeListener ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MainCustomDialog);
        mRootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_maincustom, null);

//        builder.setTitle(mResTitle == 0 ? R.string.common_alert : mResTitle);

        if(mResContentLayout !=-1){ // 커스텀 뷰 컨텐츠
            RelativeLayout rlContentLayout = (mRootView.findViewById(R.id.LL_CONTENTS_LAYOUT));
            View contentView = getActivity().getLayoutInflater().inflate(mResContentLayout, null);
            rlContentLayout.removeAllViews();
            rlContentLayout.addView(contentView);
        } else { // 기본 컨텐츠
            mTvContents = mRootView.findViewById(R.id.TV_DEFAULT_CONTENTS);
            if(mSpanContents!=null){
                mTvContents.setText(mSpanContents);
            } else if (mStrContents !=null){
                mTvContents.setText(mStrContents);
            }
        }

        // 버튼
        mBtnAloneDone = (mRootView.findViewById(R.id.BTN_ALONE_DONE));
        mBtnAloneDone.setOnClickListener(this);
        if(mResIdAloneDone !=-1) {
            mBtnAloneDone.setVisibility(View.VISIBLE);
            mBtnAloneDone.setText(mResIdAloneDone);
        }
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

        builder.setView(mRootView);
        Dialog dlg = builder.create();
        setCancelable(isCancelable);
        setIsCanceledOnTouchOutside(isCanceledOnTouchOutside);
        return dlg;
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
         if (v.getId() == R.id.BTN_ALONE_DONE) {
            if (positiveListener !=null)
                positiveListener.onClickPositive(this);
        }else if (v.getId() == R.id.BTN_CANCEL){
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

    public void setContentLayout(int resIdLayout) {
        this.mResContentLayout = resIdLayout;
    }

    public void setIsCanceledOnTouchOutside(boolean isCanceledOnTouchOutside) {
        this.isCanceledOnTouchOutside = isCanceledOnTouchOutside;
    }

    public void setTitle(int mStrTitle) {
        this.mResTitle = mStrTitle;
    }

    public void setMsgContents(String mStrContents) {
        this.mStrContents = mStrContents;
    }
    public void setMsgContents(SpannableString mStrContents) {
        this.mSpanContents = mStrContents;
    }

    public void setAloneDoneButton(int mResIdAloneDone, OnPositvelListener listener) {
        this.mResIdAloneDone = mResIdAloneDone;
        this.positiveListener = listener;
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

