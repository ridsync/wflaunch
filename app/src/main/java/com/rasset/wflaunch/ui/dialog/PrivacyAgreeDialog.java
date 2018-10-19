package com.rasset.wflaunch.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rasset.wflaunch.R;

/**
 * Created by andman on 2016-01-12.
 */
public class PrivacyAgreeDialog extends BaseDialogFragment   implements View.OnClickListener{

    private static volatile PrivacyAgreeDialog sFag;

    public void setOnDismissListener() {

    }

    public interface OnPositvelListener {
        void onClickPositive(PrivacyAgreeDialog dialog);
    }

    public static PrivacyAgreeDialog newInstance(Context context) {
        PrivacyAgreeDialog mFag = new PrivacyAgreeDialog();
        mFag.mContext = context;
        return mFag;
    }

    public PrivacyAgreeDialog() {
    }

    boolean isCancelable = true;
    OnPositvelListener positiveListener ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.FullScreenDialog);
        mRootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_privacy_agree, null);

        mBtnPositive = (mRootView.findViewById(R.id.BTN_DONE));
        mBtnPositive.setOnClickListener(this);

        builder.setView(mRootView);
        Dialog dlg = builder.create();
        setCancelable(isCancelable);
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
        if (v.getId() == R.id.BTN_DONE) {
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

}

