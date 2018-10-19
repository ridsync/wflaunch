package com.rasset.wflaunch.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.rasset.wflaunch.R;
import com.rasset.wflaunch.ui.components.RecursiveRadioGroup;

/**
 * Created by andman on 2016-01-12.
 */
public class SelectSubDiagTypeDialog extends BaseDialogFragment   implements View.OnClickListener{

    private static volatile SelectSubDiagTypeDialog sFag;

    public void setOnDismissListener() {

    }
    public void setOnPositveListener(OnPositveListener listener) {
        positiveListener = listener;
    }

    public interface OnPositveListener {
        void onClickPositive(SelectSubDiagTypeDialog dialog);
    }

    public static SelectSubDiagTypeDialog newInstance(Context context) {
        SelectSubDiagTypeDialog mFag = new SelectSubDiagTypeDialog();
        mFag.mContext = context;
        return mFag;
    }

    public SelectSubDiagTypeDialog() {
    }

    boolean isCancelable = true;
    OnPositveListener positiveListener ;
    public Boolean isTypeTax = false;
    public RecursiveRadioGroup recGroup;
    public RadioButton rbCatNo1;
    public RadioButton rbCatNo2;
    public RadioButton rbCatNo3;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.FullScreenDialog);
        mRootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_select_sub_category, null);

        mBtnPositive = (mRootView.findViewById(R.id.BTN_DONE));
        recGroup = (mRootView.findViewById(R.id.RG_DIAG_RESTATE_TYPE));
        rbCatNo1 = (mRootView.findViewById(R.id.RB_CAT_NO1));
        rbCatNo2 = (mRootView.findViewById(R.id.RB_CAT_NO2));
        rbCatNo3 = (mRootView.findViewById(R.id.RB_CAT_NO3));

        if (isTypeTax) {
            rbCatNo1.setText("주택/수익형부동산 분야");
            rbCatNo2.setText("농지절세 분야");
            rbCatNo3.setText("모두 선택");
        }

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

