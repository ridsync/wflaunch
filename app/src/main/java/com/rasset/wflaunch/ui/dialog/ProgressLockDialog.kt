package com.rasset.wflaunch.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.view.Window
import com.rasset.wflaunch.R

/**
 * Created by devok on 2018-09-04.
 */

class ProgressLockDialog(context: Context, themeResId:Int) : Dialog(context,themeResId) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //
        setContentView(R.layout.dialog_progress)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    public override fun onStart() {
        super.onStart()
    }

    public override fun onStop() {
        super.onStop()
    }

}