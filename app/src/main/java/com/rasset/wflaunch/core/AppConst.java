package com.rasset.wflaunch.core;


import com.rasset.wflaunch.BuildConfig;

/**
 * Created by andman on 2015-11-30.
 */
public class AppConst {
    public static final String PACKAGE_NAME = "com.playcorp.sqapp";

    public static final String BUILD_TYPE_DEBUG = "debug";
    public static final String BUILD_TYPE_RELEASE = "release";
    public static final String BUILD_TYPE_QA = "qa";


    public static final int MORE_LIST_LIMIT_UNIT = 30;
    public static final int LEAST_ID_CNT = 4;
    public static final int LEAST_PASSWORD_CNT = 4;

    public static final int CAPTURE_IMAGE_MAX_SIZE = 1024;
    public static final int IMAGE_MAX_SIZE = 1920;
    public static final int SUFFIX_SMALL = 0;
    public static final int SUFFIX_MEDIUM = 1;
    public static final int SUFFIX_LARGE = 2;
    public static final int SUFFIX_XLARGE = 3;


    // View
    public static final int FRAGMENT_DEFAULT = -1;
    public static final int FRAGMENT_BOTNAV_MAIN = 10000;
    public static final int FRAGMENT_USER_LIST = FRAGMENT_BOTNAV_MAIN+1;

    public static final String FRAG_NAME_DIAG_CUSTOMER_INFO = "회원 기본정보";
    public static final String FRAG_NAME_DIAG_INFO_STEP1 = "회원 예진정보1";
    public static final String FRAG_NAME_DIAG_INFO_STEP2 = "회원 예진정보2";
    public static final String FRAG_NAME_DIAG_COMPLETED = "FRAG_NAME_DIAG_COMPLETED";

    public static final String ADVISOR_NAME_INVEST = "김민수";
    public static final String ADVISOR_NAME_MD = "고상철";
    public static final String ADVISOR_NAME_TAX = "김윤석";
    public static final String ADVISOR_NAME_HOME_INTE = "김영숙";
    public static final String ADVISOR_NAME_MANAGEMENT = "박진상";
    public static final String ADVISOR_NAME_CM = "이택수";

    //
    public static final String COMMON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; // "2014-11-24T16:23:00.000Z"
    public static final String COMMON_NOTIFY_REPLACE_FORMAT = "$send_login_id$"; // notify type id syntax

    public static final String SENDER_ID = "18587380099";


    // gcm
    public static final String NOTIFICATION_CHANNEL_ID = "SqappChannel";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String REGISTRATION_COMPLETE = "gcm_regist_completed";
    public static final String INTENT_EXTRA_ISRUNNING = "running";

    // Net Base URLs
    public static String API_MAIN_URL = BuildConfig.API_BASE_URL;

    // Net URLs Setting
    public static final String API_MAIN_URL_REL = "https://api.saycupid.co.kr/prod/";
    public static final String API_FILE_UPLOAD_URL_REL = "http://upload.api.saycupid.com:1377";
    public static final String API_IMAGE_URL_REL = "http://static.saycupid.com/images/member_pic/";

    public static final String API_MAIN_URL_DEV = "https://api.saycupid.co.kr/dev/";
    public static final String API_FILE_UPLOAD_URL_DEV = "http://upload.api.saycupid.com:1377";
    public static final String API_IMAGE_URL_DEV = "http://static.saycupid.com/images/member_pic/";

    public static final String API_FACETALK_URL = "http://app.api.saycupid.com:1381";

    // Bundle
    public static final String BUNDLE_UID = "BUNDLE_UID";
    public static final String BUNDLE_USERID = "BUNDLE_USERID";

    // Dialog
    public static final String DIALOG_LOGIN_FAIL = "DIALOG_LOGIN_FAIL";
    public static final String DIALOG_ALERT_EMPTY_DIAG = "DIALOG_ALERT_EMPTY_DIAG";
    public static final String DIALOG_CUSTOMER_INFO_PRIVACY = "DIALOG_CUSTOMER_INFO_PRIVACY";


    // pref

    public static final String PREFERENCE_USERINFO_ID = "PREFERENCE_USERINFO_ID";
    public static final String PREFERENCE_USERINFO_NAME = "PREFERENCE_USERINFO_NAME";
    public static final String PREFERENCE_USERINFO_PHOTO = "PREFERENCE_USERINFO_PHOTO";
    public static final String PREFERENCE_FIRST_LAUNCH = "PREFERENCE_FIRST_LAUNCH";

    // setting Push
    public static final String PREFERENCE_SETTING_NOTIFY_ALL = "PREFERENCE_SETTING_NOTIFY_ALL";

    // FireBase Analytics
    public static final String FA_PARAM_SIGNUP_NORMAL = "normal";
    public static final String FA_PARAM_SIGNUP_FB = "facebook";
    public static final String FA_PARAM_SIGNUP_GOOGLE = "google";
    public static final String FA_PARAM_IOEXECTION = "exection_type";
    public static final String FA_PARAM_PUSH_TYPE = "push_type";
    public static final String FA_PARAM_PRODUCT_TYPE = "product_type";
    public static final String FA_PARAM_PRODUCT_ID = "product_id";
    public static final String FA_PARAM_REQUEST_TYPE = "request_type";
    public static final String FA_PARAM_FRAG_TYPE = "frag_type";

    // ANALYTICS
    public static final String ANALYTICS_EVENT_CONNECTION_ERROR = "connection_error";
    public static final String ANALYTICS_EVENT_FT_CONNECTION_ERROR = "facetalk_con_error";
    public static final String ANALYTICS_EVENT_ACTIVEUSER_24 = "활성사용자_in24";
}
