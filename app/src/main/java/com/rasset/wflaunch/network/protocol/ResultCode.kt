package com.rasset.wflaunch.network.protocol

/**
 * Created by devok on 2018-09-03.
 */

/**
 * @author okc
 * @version 1.0
 * @see
 * @since 2015-12-20.
 *
 * protocol -
 */
class ResultCode {

    companion object {
        /**
         * HTTP, CLIENT PROCESS
         */
        const val HTTP_SUCCESS_OK = 200
        const val CLIENT_ERROR = 300
        const val JSON_PRASE_ERROR = 301
        const val SESSION_ERROR = 400
        const val SERVER_ERROR = 500
        const val UNKNOWN_ERROR = -100
        const val CLIENT_CANCELLED = -303
        const val CONNECTION_FAIL = -300 // 서버접속실패
        const val CONNECTION_NOT_AVAILABLE = -909 // 인터넷 연결 불가능
        const val IO_EXECPTION_ERROR = -309 // ?? 서버접속실패 ??

        /**
         * Project
         */
        // Common Success 1
        const val API_NO_ERROR = HTTP_SUCCESS_OK
        const val API_SUCCESS = 1

        // Common Fail Code Range 21 ~
        const val API_AUTH_NOT_EXIST_USER = 21
        const val API_AUTH_NOT_EXIST_OTHERUSER = 22
        const val API_CLIENT_OLD_VERSION = 41
        const val API_CLIENT_PARAM_ERROR = 42
        const val API_SERVER_DB_DOWN_ERROR = 51
        const val API_SERVER_DB_QUERY_ERROR = 52
        const val API_UNKNOWN_ERRROR = 53
        const val API_WARNING_SERVERDOWN = 99

        // FAil Code Range 1~20
        // login & Regist
        const val API_LOGIN_UNREGIST_USERID = 2
        const val API_LOGIN_UNREGIST_LOGINID = 3
        const val API_LOGIN_INCORRECT_PASSWD = 4
        const val API_LOGIN_USER_BAN = 3003
        const val API_ID_ALREADY_EXIST = 2

        // Profile
        const val API_USER_LIKE_ALREADY = 2
        const val API_USER_LIKE_HAS_CANCELED = 3
        const val API_USER_LIKE_TARGETUSER_BAN_OR_REJECT = 4

        // Kontact
        const val API_CONTACT_TYPE_NONE_REQUEST = 9
        const val API_CONTACT_TYPE_REQUEST = 0
        const val API_CONTACT_TYPE_READ = 1
        const val API_CONTACT_TYPE_ACCEPT = 2
        const val API_CONTACT_TYPE_REJECT = 3
        const val API_CONTACT_TYPE_REJECT_AUTO = 4
        // result PostContact
        const val API_CONTACT_REQ_TYPE_ALREADY = 2
        const val API_CONTACT_REQ_TYPE_EMPTY_KONTACT_INFO = 4

        // Payment
        const val REESULT_TYPE_PURCHASE_SUCCESS = 1
        const val REESULT_TYPE_PURCHASE_NOPERMIT = 2
        const val REESULT_TYPE_PURCHASE_ALREADY_SUCC = 3
        const val REESULT_TYPE_CONSUME_SUCCESS = 1
        const val REESULT_TYPE_CONSUME_NOPERMIT = REESULT_TYPE_PURCHASE_NOPERMIT
        const val REESULT_TYPE_CONSUME_ALREADY_CONSUME = 4
        const val REESULT_TYPE_CONSUME_PURCHASE_NONE_CONSUME = 5


        // Chat
        const val API_POST_CHAT_SUCCESS = 1
        const val API_POST_CHAT_LIMIT_ROOM_COUNT = 2
        const val API_POST_CHAT_NOTENOUGH_HEART = 3

        const val API_GET_CHATLIST_NEED_HEART_MALE = 1 // 남자:채팅5회이상하트컨슘필요
        const val API_GET_CHATLIST_HEART_CONSUMED = 2


        // Upload Photo
        const val API_POST_UPLOAD_PHOTO_SUCCESS = 1
        const val API_POST_UPLOAD_PHOTO_3DAY_LIMIT = 2
    }
}
