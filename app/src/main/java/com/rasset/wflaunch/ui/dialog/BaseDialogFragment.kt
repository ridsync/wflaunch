package com.rasset.wflaunch.ui.dialog

import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.Button
import android.widget.TextView

/**
 * Created by devok on 2018-09-04.
 */
open class BaseDialogFragment : DialogFragment() {

    var mOnViewClickListener: BaseDialogFragment.OnViewClickListener? = null
    var mOnClickListener: BaseDialogFragment.OnClickListener? = null
    var mOnDismissListener: BaseDialogFragment.OnDismissListener? = null
    lateinit var  mContext: Context
    lateinit var mRootView: View
    var data: Any? = null
    lateinit var mTvContents: TextView
    lateinit var mBtnPositive: Button
    lateinit var mBtnNegative: Button

    interface OnViewClickListener {
        fun onClick(view: View, dialog: BaseDialogFragment)
    }

    interface OnClickListener {
        fun onClickPositive(dialog: BaseDialogFragment)

        fun onClickNegative(dialog: BaseDialogFragment)

        fun onClickNeutral(dialog: BaseDialogFragment)
    }

    interface OnDismissListener {
        fun onDismiss(dialog: BaseDialogFragment)
    }

    interface OnItemClickListener {
        fun onItemClick(dialog: BaseDialogFragment, which: Int)
    }

    interface OnMultiItemClickListener {
        fun onItemClick(dialog: BaseDialogFragment, which: Int, isChecked: Boolean)
    }

    fun setOnViewClickListener(listener: BaseDialogFragment.OnViewClickListener) {
        mOnViewClickListener = listener
    }

    fun setOnClickListener(listener: BaseDialogFragment.OnClickListener) {
        mOnClickListener = listener
    }

    fun setOnDismissListener(listener: BaseDialogFragment.OnDismissListener) {
        mOnDismissListener = listener
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        mOnDismissListener?.let { it.onDismiss(this) }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        data = null
    }

}