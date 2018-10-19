package com.rasset.wflaunch.network.task;

import android.content.Context;

import com.rasset.wflaunch.network.protocol.ParamKey;
import com.rasset.wflaunch.network.res.BaseModel;
import com.google.gson.JsonElement;
import  com.rasset.wflaunch.network.OnNetworkListener;
import  com.rasset.wflaunch.network.protocol.ReqType;
import com.rasset.wflaunch.network.retrofit.MainAPInterface;
import com.rasset.wflaunch.network.retrofit.RetroRestAPIService;

import org.json.JSONException;

import java.io.IOException;

import retrofit2.Call;

/**
 * @author okc
 * @version 1.0
 * @see
 * @since 2015-12-20.
 */
public class MainListTask extends NetworkTask<BaseModel> {

    public MainListTask() {
        super();
    }

    public MainListTask(Context mContext, int requestType, OnNetworkListener onNetworkListener) {
        super(mContext, requestType, onNetworkListener);
    }

    // 상속 구현 ResInterfce API 생성
    protected Call<? extends BaseModel> getRetroCallable() {
        MainAPInterface service = RetroRestAPIService.createService(MainAPInterface.class);
        // reqType별 API WindowsTopViewService 별도 반환
        if (ReqType.REQUEST_TYPE_POST_USER_LOGIN == mRequestType) {
            return service.reqPostUserLogin(mParams);
        }  else if (ReqType.REQUEST_TYPE_GET_DIAGNOSE_LIST == mRequestType) {
            return service.reqGetDiagnoseList(mParams.get(ParamKey.PARAM_USERID).toString());
        } else if (ReqType.REQUEST_TYPE_POST_DIAGNOSE_UPDATE == mRequestType) {
            return service.reqPostDiagnoseUpdate(mParams);
        } else {
            return null;
        }
    }

    // Retrofit JsonElement로부터 직접파싱?
    @Override
    protected BaseModel onParse(JsonElement json, int requestType) throws JSONException, IOException {
        return null;
    }
}
