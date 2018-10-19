package com.rasset.wflaunch.network;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @Deprecated
 */
public abstract class CancelableCallback<T> implements Callback<T> {

    private static List<CancelableCallback> mList = new ArrayList<>();
    private static final Object syncObj1 = new Object();
    private boolean isCanceled = false;
    private int mTag = 0;

    public CancelableCallback() {
            CancelableCallback.addOnSync(this);
    }

    public CancelableCallback(int tag) {
            mTag = tag;
            CancelableCallback.addOnSync(this);
    }

    @Override
    public final void onResponse(Call<T> call,  Response<T> response) {
        if (!isCanceled)
            onRetroSuccess( call, response);
        removeOnSync(this);
    }

    @Override
    public final void onFailure(Call<T> call, Throwable t) {
        if (!isCanceled)
            onRetroFailure(call,t);
        removeOnSync(this);
    }

    public abstract void onRetroSuccess(Call<T> call, Response<T> retrofit);

    public abstract void onRetroFailure(Call<T> call,Throwable t);

    public static void addOnSync(CancelableCallback callback){
        synchronized(syncObj1){
            mList.add(callback);
        }
    }

    public static void removeOnSync(CancelableCallback callback){
        synchronized(syncObj1){
            mList.remove(callback);
        }
    }

    public  static void  cancelAll() {
        synchronized(syncObj1) {
            for (CancelableCallback callback : mList) {
                callback.cancel();
            }
        }
    }

    public void cancel(int tag) {
        if (tag != 0)
            for (CancelableCallback callback : mList) {
                if (tag == callback.mTag )
                    callback.cancel();
            }
    }

    public void cancel() {
            isCanceled = true;
    }

}
