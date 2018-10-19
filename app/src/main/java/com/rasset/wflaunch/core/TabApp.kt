package com.rasset.wflaunch.core

import android.app.Application
import android.content.Context
import com.rasset.wflaunch.model.UserInfo
import com.rasset.wflaunch.network.retrofit.RetroRestAPIService
import com.rasset.wflaunch.utils.Prefer

/**
 * Created by devok on 2018-08-30.
 */
class TabApp : Application() {

    companion object {
        lateinit var mContext: Context
        var userInfo: UserInfo? = null
            get() {
                return if (field == null) {
                    val userId = Prefer.getPreferenceLong(AppConst.PREFERENCE_USERINFO_ID,mContext)
                    val userPhoto = Prefer.getPreferenceString(AppConst.PREFERENCE_USERINFO_PHOTO,mContext)
                    val userName = Prefer.getPreferenceString(AppConst.PREFERENCE_USERINFO_NAME,mContext)
                    UserInfo(userId,userName,userPhoto)
                } else {
                    field
                }
            }
            set(value) {
                if (value != null){
                    Prefer.setSharedPreference(AppConst.PREFERENCE_USERINFO_ID, value.userId,mContext)
                    Prefer.setSharedPreference(AppConst.PREFERENCE_USERINFO_PHOTO, value.photoImgPath,mContext)
                    Prefer.setSharedPreference(AppConst.PREFERENCE_USERINFO_NAME, value.userName,mContext)
                    field = value
                }
            }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        RetroRestAPIService.initRetrofit(AppConst.API_MAIN_URL, null)
    }
}
