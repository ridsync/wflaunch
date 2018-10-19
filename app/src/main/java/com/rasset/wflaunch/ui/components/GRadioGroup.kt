package com.rasset.wflaunch.ui.components

import android.view.View
import android.view.ViewParent
import android.widget.RadioButton
import android.widget.RadioGroup

import java.util.ArrayList

/**
 * Created by devok on 2018-09-13.
 */
internal class GRadioGroup {

    var radios: MutableList<RadioButton> = ArrayList()

    /**
     * This occurs everytime when one of RadioButtons is clicked,
     * and deselects all others in the group.
     */
    var onClick: View.OnClickListener = View.OnClickListener { v ->
        // let's deselect all radios in group
        for (rb in radios) {

            val p = rb.parent
            if (p.javaClass == RadioGroup::class.java) {
                // if RadioButton belongs to RadioGroup,
                // then deselect all radios in it
                val rg = p as RadioGroup
                rg.clearCheck()
            } else {
                // if RadioButton DOES NOT belong to RadioGroup,
                // just deselect it
                rb.isChecked = false
            }
        }

        // now let's select currently clicked RadioButton
        if (v.javaClass == RadioButton::class.java) {
            val rb = v as RadioButton
            rb.isChecked = true
        }
    }

    /**
     * Constructor, which allows you to pass number of RadioButton instances,
     * making a group.
     *
     * @param radios One RadioButton or more.
     */
    constructor(vararg radios: RadioButton) : super() {

        for (rb in radios) {
            this.radios.add(rb)
            rb.setOnClickListener(onClick)
        }
    }

    /**
     * Constructor, which allows you to pass number of RadioButtons
     * represented by resource IDs, making a group.
     *
     * @param activity  Current View (or Activity) to which those RadioButtons
     * belong.
     * @param radiosIDs One RadioButton or more.
     */
    constructor(activity: View, vararg radiosIDs: Int) : super() {

        for (radioButtonID in radiosIDs) {
            val rb = activity.findViewById<View>(radioButtonID) as RadioButton
            if (rb != null) {
                this.radios.add(rb)
                rb.setOnClickListener(onClick)
            }
        }
    }
}
