package com.rasset.wflaunch.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialog;

import com.rasset.wflaunch.R;


/**
 * Created by ridsync on 2015-011-30.
 *  기본 Alert Dialog   import android.support.v7.app.AlertDialog
 *  1) AlertDialog
 *  2) Title | Conetnt Txt | 확인 | 취소
 *  3) Theme (base Material)
 *
 *   미 완 성
 */
public class AlertSimpleDialog extends DialogFragment {

    private static final String ARG_AUTOCLOSE_TIME_MS = "autoClose";

    private OnPositvelListener onPositveListener;
    private OnNegativelListener onNegativeListener;
    private OnNeutralListener onNeutralListener;
    private OnCancelListener onCancelListener;
    private OnDismissListener onDismisslListener;

    private int iconResId = 0;
    private int title = 0;
    private int Message = 0;
    private CharSequence CharMessage = null;
    private int btnOk = 0;
    private int btnCancel = 0;
    private int btnNewtrol = 0;
    private int isAutoCloseMs = 0;
    private android.support.v7.app.AlertDialog.Builder builder;

    public AlertSimpleDialog(){
        setArguments(getArguments());
    }

    @SuppressLint("ValidFragment")
    public AlertSimpleDialog(Context appContext) {
//        builder =  new android.support.v7.app.AlertDialog.Builder(appContext, R.style.MaterialAlertDialog);
    }

    public interface OnPositvelListener {
        void onClickPositive(AlertSimpleDialog dialog);
    }
    public interface OnNegativelListener {
        void onClickNegative(AlertSimpleDialog dialog);
    }
    public interface OnNeutralListener {
        void onClickNeutral(AlertSimpleDialog dialog);
    }
    public interface OnCancelListener {
        void onCancel(AlertSimpleDialog dialog);
    }
    public interface OnDismissListener {
        void onDismiss(AlertSimpleDialog dialog);
    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();

        builder = new android.support.v7.app.AlertDialog.Builder(getActivity(), R.style.TransparentDialog);
        builder.setTitle(title==0? R.string.common_alert : title);
        if(CharMessage!=null){
            builder.setMessage(CharMessage);
        } else {
            builder.setMessage(Message==0? R.string.common_alert : Message);
        }
        if (iconResId !=0) {
            builder.setIcon(iconResId);
        }
        if (btnOk!=0) {
            builder.setPositiveButton(getString(btnOk), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (onPositveListener != null) {
                        onPositveListener.onClickPositive(AlertSimpleDialog.this);
                    }
                }
            });
        }
        if (btnCancel!=0) {
            builder.setNegativeButton(getString(btnCancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (onNegativeListener != null) {
                        onNegativeListener.onClickNegative(AlertSimpleDialog.this);
                    }
                }
            });
        }
        if (btnNewtrol!=0) {
            builder.setNeutralButton(getString(btnNewtrol), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (onNeutralListener != null) {
                        onNeutralListener.onClickNeutral(AlertSimpleDialog.this);
                    }
                }
            });
        }
        if (isAutoCloseMs > 0 ){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(getDialog() != null && getDialog().isShowing())
                        dismiss();
                }
            }, isAutoCloseMs);
        }
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (onCancelListener != null){
            onCancelListener.onCancel(AlertSimpleDialog.this);
        }
        super.onCancel(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if(onDismisslListener!=null){
            onDismisslListener.onDismiss(AlertSimpleDialog.this);
        }
        super.onDismiss(dialog);
    }

    public void setTitle(int resId) {
        title = resId;
    }
    public void setIcon(int resIconId) {
        iconResId = resIconId;
    }

    public void setMessage(int resId) {
        Message = resId;
    }

    public void setMessage(CharSequence charSequence) {
        CharMessage = charSequence;
    }


    public void setAutoCloseTime(int mDelayTime) {
        isAutoCloseMs = mDelayTime;
    }

    public void setPositiveButton(int resId , OnPositvelListener positiveListener) {
        btnOk = resId;
        onPositveListener = positiveListener;
    }

    public void setPositiveButton(int resId) {
        setPositiveButton(resId,null);
    }

    public void setNegativeButton(int resId ) {
        setNegativeButton(resId,null);
    }

    public void setNegativeButton(int resId , OnNegativelListener onNegativeListener) {
        btnCancel = resId;
        this.onNegativeListener = onNegativeListener;
    }

    public void setNeutralButton(int resId , OnNeutralListener onNeutralListener) {
        btnNewtrol = resId;
        this.onNeutralListener = onNeutralListener;
    }

    public void setNeutralButton(int resId ) {
        setNeutralButton(resId,null);
    }

    public void setDismissListener(OnDismissListener onDismissListener) {
        this.onDismisslListener = onDismissListener;
    }

    public void setCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }
}

