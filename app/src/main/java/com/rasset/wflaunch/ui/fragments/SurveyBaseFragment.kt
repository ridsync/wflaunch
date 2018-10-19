package com.rasset.wflaunch.ui.fragments

import com.rasset.wflaunch.model.DiagnoseBaseInfo
import com.rasset.wflaunch.model.DiagnoseInfo

/**
 * Created by devok on 2018-09-27.
 */
open abstract class SurveyBaseFragment : BaseFragment(){

    abstract fun isValidDiagInputs(): Boolean

    abstract fun getDiagDatas():DiagnoseBaseInfo?

}
