package com.rasset.wflaunch.network.task;

import android.content.Context;
import android.os.AsyncTask;

import com.rasset.wflaunch.network.res.BaseModel;
import com.rasset.wflaunch.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.rasset.wflaunch.network.OnNetworkListener;
import com.rasset.wflaunch.network.OnNetworkStateListener;
import com.rasset.wflaunch.network.protocol.ResultCode;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;

/**
 * @author okc
 * @version 1.0
 * @see
 * @since 2015-12-19.
 */
public abstract class NetworkTask<T> extends AsyncTask<Void, Void, BaseModel> {

    protected int mRequestType;
    protected Context mContext;
    protected OnNetworkListener mNetworkListener; // Netowk Data Result
    protected boolean isRetroParsing = true;
    protected int mErrorCode = ResultCode.API_NO_ERROR;
    protected String mServerErrorMsg = "NOERROR";

    HashMap<String,Object> mParams = new HashMap<>();

    protected OnNetworkStateListener stateListener; // Netowk Connection Status

    public NetworkTask() {
        super();
    }
    /**
     *
     * @param mContext
     * @param reqType : int mRequestType Field로 존재
     * @param onNetworkListener
     */
    public NetworkTask(Context mContext, int reqType, OnNetworkListener onNetworkListener) {
        super();
        this.mContext = mContext;
        this.mRequestType = reqType;
        this.mNetworkListener = onNetworkListener;
    }

    public void setNetStateListener(OnNetworkStateListener stateListener) {
        this.stateListener = stateListener;
    }
    public OnNetworkStateListener getNetStateListener() {
        return this.stateListener;
    }

    public void setmRequestType(int mRequestType) {
        this.mRequestType = mRequestType;
    }

    public int getRequestType() {
        return this.mRequestType;
    }

    public OnNetworkListener getNetWorkListener() {
        return this.mNetworkListener;
    }
    public void setNetworkListener(OnNetworkListener netowrkListener) {
        this.mNetworkListener = netowrkListener;
    }
    public void setContext(Context context){
        this.mContext = context;
    }

    public Context getContext(){
        return mContext;
    }

    public void setParam(HashMap<String,Object> params) {
        this.mParams = params;
    }

    public HashMap<String,Object> getParams() {
        return this.mParams;
    }

    public void addParam(String key , Object value) {
        mParams.put(key, value);
    }

    @Override
    protected void onCancelled() {
        switch (getStatus()) {
            case RUNNING:
                if(null != stateListener){
                    stateListener.onStateFail(ResultCode.CLIENT_CANCELLED, mRequestType, this);
                }
                break;
        }
        Logger.d(this, "onCancelled mRequestType = " + mRequestType);
        super.onCancelled();
    }
    @Override
    protected void onPreExecute() {
        if(isCancelled()){
            Logger.d(this, "NetTask -- Canceld");
        }
        else {
            startProgressBar();
        }
    }

    @Override
    protected void onPostExecute(BaseModel result) {
        if(isCancelled()){
            if(null != stateListener){
                stateListener.onStateFail(mErrorCode, mRequestType, this);
            }
            stopProgressBar();
            return;
        }

        // http status check : 200
        if(mErrorCode != ResultCode.API_NO_ERROR
                || result == null){
            if(null != mNetworkListener){
                mNetworkListener.onNetFail(mErrorCode, mServerErrorMsg, mRequestType);
            }
            /**
             *  (1)서버접속 실패 상태
             */
            if(null != stateListener){
                stateListener.onStateFail(mErrorCode, mRequestType, this);
            }
            stopProgressBar();
            return ;
        }
        // api server status check : 1
        try {
            if (result.getResVal() == ResultCode.API_SUCCESS && null != mNetworkListener ){
                mNetworkListener.onNetSuccess(result, mRequestType);
            } else {
                if(null != mNetworkListener) {
                    mNetworkListener.onNetFail(result.getResVal(), result.getResMsg(), mRequestType);
                }
                // Server Fail 로깅
                if(result.getResVal() == ResultCode.API_SERVER_DB_DOWN_ERROR
                        || result.getResVal() == ResultCode.API_SERVER_DB_QUERY_ERROR){
//                    if (!AUtil.isDebugDevice(mContext)){
//                        Answers.getInstance().logCustom(new CustomEvent("[Net] CLIENT_CONNECTION_FAIL")
//                                .putCustomAttribute("ReqType", mRequestType+"")
//                                .putCustomAttribute("ExecptionMsg", result.retMsg == null ? result.getResVal()+"" : result.retMsg )
//                                .putCustomAttribute("resultCode", mErrorCode+""));
//                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
//            Crashlytics.logException(new SQAppException("[NetowrkTask] Exception Error : ", "", e));
        } finally {
            if(null != stateListener){
                stateListener.onStateSucess(mRequestType, this);
            }
            stopProgressBar();
            this.cancel(true);
        }
    }

    @Override
    protected BaseModel doInBackground(Void... params) {

        if(isCancelled()){
            return null;
        }
        try {
            retrofit2.Response response = getRetroCallable().execute();
            // http status check
            if (response == null){
                mErrorCode = ResultCode.UNKNOWN_ERROR;
                mServerErrorMsg = "ErrorMsg.UNKNOWN_ERROR" + "retrofit.Response is Null";
                Logger.d(NetworkTask.this,"[NetworkTask] retrofit.Response is Null mRequestType:" + mRequestType);
                return null;
            } else if (response.code() != ResultCode.HTTP_SUCCESS_OK){
                mErrorCode = response.code();
                mServerErrorMsg = "ErrorMsg.SERVER_ERROR" + response.errorBody().string();
                Logger.d(NetworkTask.this,"[NetworkTask] retrofit.Response Fail mErrorCode:" + mErrorCode + " RequestType:" + mRequestType);
                return null;
            }
            if (isRetroParsing){ // TODO
                return (BaseModel) response.body();
            } else {
                return (BaseModel) onParse((JsonElement)response.body() , mRequestType);
            }

        } catch (IOException e) {
            mErrorCode = ResultCode.CONNECTION_FAIL;
            mServerErrorMsg = "ErrorMsg.IO_EXECPTION_ERROR";
            e.printStackTrace();

        } catch (JSONException e) {
            mErrorCode = ResultCode.JSON_PRASE_ERROR;
            mServerErrorMsg = "ErrorMsg.JSON_PRASE_ERROR";
            e.printStackTrace();
        } catch (Exception e) {
            mErrorCode = ResultCode.CLIENT_ERROR;
            mServerErrorMsg = "ErrorMsg.CLIENT_ERROR";
            e.printStackTrace();
//            Logger.w("Exception :" + mErrorString);
        } finally {
        }
        Logger.d(NetworkTask.this,"[NetworkTask] retrofit.Response Fail mErrorCode:" + mErrorCode
                + " / mServerErrorMsg:" + mServerErrorMsg + " / RequestType:" + mRequestType);
        return null;
    }

    abstract protected Call<? extends BaseModel> getRetroCallable();

    abstract protected T onParse(JsonElement json,int requestType)  throws JSONException, IOException;

    protected T onGsonParsing(String data,  Class<?> classOfT){
        Gson gson = new Gson();
        return (T) gson.fromJson(data, classOfT);
    };

    /**
     *  (2)단말 인터넷연결 시도 실패 상태
     */
    public void notifyConnectFail() {
        if(null != stateListener){
            stateListener.onStateFail(ResultCode.CONNECTION_NOT_AVAILABLE, mRequestType, this);
        }
        // 위 콜백과 이 실패콜백도 보내기때문에 동시에 실패처리가 Notify 될 수 있다.
        if(null != mNetworkListener){
            mNetworkListener.onNetFail(ResultCode.CONNECTION_NOT_AVAILABLE, "CONNECTION_NOT_AVAILABLE", mRequestType);
        }
        stopProgressBar();
    }

    public final void startProgressBar() {
        if (null != mNetworkListener) {
            mNetworkListener.onProgresStart(mRequestType);
        }
    }

    public final void stopProgressBar() {

        if (null != mNetworkListener) {
            mNetworkListener.onProgresStop(mRequestType);
        }
    }

}
