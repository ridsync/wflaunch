package com.rasset.wflaunch.network.protocol

/**
 * Created by devok on 2018-09-03.
 */

class ReqType {
    companion object {

        const val REQUEST_TYPE_GET_APIBASE_URL = 99991

        const val REQUEST_TYPE_POST_USER_LOGIN = 700000
        const val REQUEST_TYPE_GET_DIAGNOSE_LIST = REQUEST_TYPE_POST_USER_LOGIN + 1
        const val REQUEST_TYPE_POST_DIAGNOSE_UPDATE = REQUEST_TYPE_GET_DIAGNOSE_LIST + 1

    }
}