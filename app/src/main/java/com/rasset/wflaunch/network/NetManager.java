package com.rasset.wflaunch.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

import com.rasset.wflaunch.network.res.BaseModel;
import com.rasset.wflaunch.network.task.NetworkTask;
import com.rasset.wflaunch.utils.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**
 * @author okc
 * @version 1.0
 * @see
 * @since 2015-12-16.
 *
 * @TODO AsyncTask 대체구현시 EXecutor EXecutorService 이용한 쓰레드 관리
 */
public class NetManager {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    static HashMap<NetworkTask<?>, NetworkTask<?>> mSet = new HashMap<NetworkTask<?>, NetworkTask<?>>();

    public NetManager() {
    }

    public static void startTaskOnDemooo(NetworkTask<?> task, BaseModel data){
        task.getNetWorkListener().onNetSuccess(data,task.getRequestType());
        task.getNetWorkListener().onProgresStop(task.getRequestType());
        Logger.d(NetManager.class, "Current Task OnDemooo !!" + task);
        task.cancel(true);
    }

    public static void startTask(NetworkTask<?> task){
        cancelTask(task);

        task.setNetStateListener(new OnNetworkStateListener() {

            @Override
            public void onStateSucess(int reqType, NetworkTask<?> task) {
                mSet.remove(task); // task remove
                // 네트워크상태 알림바 Off
                // TODO EventBus로 변경 ?? 커플링 제거 ??
//                EventBus.getDefault().post(new EbNetStat());
            }
            @Override
            public void onStateFail(int resultCode, int reqType, NetworkTask<?> task) {
                mSet.remove(task); // task remove
                //  네트워크상태 알림바 On
//                if(resultCode != ResultCode.CLIENT_CANCELLED)
//                    EventBus.getDefault().post(new EbNetStat(resultCode,task));
            }
        });
        mSet.put(task, task);

        if(checkNetStat(task.getContext(), task)==true){
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public static void cancelTask(NetworkTask<?> item){
        NetworkTask<?> task = mSet.remove(item);
        if(null != task)
        {
            Logger.i(NetManager.class, "--------cancel------------");
            switch(task.getStatus()){
                case RUNNING:
                case PENDING:
                    task.cancel(true);
                    task.stopProgressBar();
                    break;
            }
        }
//		mSet.remove(item);
    }

    public static void cancelAllTask(){
        if(!mSet.isEmpty()){
            synchronized(mSet){
                Set<NetworkTask<?>> taskSet = mSet.keySet();
                if(null != taskSet){
                    Iterator<NetworkTask<?>> iter = taskSet.iterator();
                    if(null != iter)
                    {
                        NetworkTask<?> task;
                        while(iter.hasNext()){
                            task = iter.next();
                            if(null != task && task.getStatus()== AsyncTask.Status.RUNNING){
                                task.cancel(true);
                                Logger.d(NetManager.class, "cancelAll _task="+task);
                            }
                        }
                    }
                }
                mSet.clear();
            }
        }
    }

    public static void removeTask(NetworkTask<?> item){
        NetworkTask<?> task = mSet.remove(item);
        if(null != task)
        {
            Logger.i(NetManager.class, "--------removeTask------------");
            switch(task.getStatus()){
                case RUNNING:
                case PENDING:
                    task.cancel(true);
                    break;
            }
        }
    }

    private static boolean checkNetStat(Context context, final NetworkTask<?> task){

        // TODO 온라인여부 체크 로직
        if(isOnline(context)){
            if(false == wifiAvail(context)){
                Logger.d(NetManager.class, "3G연결");
            }
            return true;
        }
        if(task!=null){
            task.notifyConnectFail();
        }
        return false;
    }

    /**
     *
     * TODO 네트워크 상태체크 유효성 ??
     * TODO  -> 체크유틸 로 이동 ??
     *
     **/

    public static boolean wifiAvail(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiAvail = ni.isAvailable();
        return isWifiAvail;
    }
    public static boolean mobileAvail(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileAvail = ni.isAvailable();
        return isMobileAvail;
    }

    public static boolean isWifiConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConnected = ni.isConnected();
        return isWifiConnected;
    }

    /**
     * 3G or WiFi 사용 가능 여부
     * @param context
     * @return true : 3G or WiFi 사용 가능
     */
    public static boolean isOnline(Context context)
    {
        if (context == null) return false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isAvailable() &&
                cm.getActiveNetworkInfo().isConnected())
        {
            return true;
        }

        return false;
    }

    public boolean isGoodNetworkStatus(Context context) {
        boolean isNetworkConnected = false;

        ConnectivityManager ctvMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (ctvMgr != null) {
            NetworkInfo info = ctvMgr.getActiveNetworkInfo();
            if (info != null) {
                NetworkInfo.State state = info.getState();
                if (state == NetworkInfo.State.CONNECTED) {
                    int nType = info.getType();
                    String strTypeName = info.getTypeName();
                    if (ConnectivityManager.TYPE_WIFI == nType || "Ethernet".equals(strTypeName)) {
                        isNetworkConnected = true;
                        //return isNetworkConnected;
                    }
                    if (ConnectivityManager.TYPE_MOBILE == nType && isWifiEnabled(context)) {
                        isNetworkConnected = true;
                        //return isNetworkConnected;
                    }
                }
            }
        }
        return isNetworkConnected;
    }

    public boolean isWifiEnabled(Context context) {
        Object result = false;
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        Method[] wmMethods = wifi.getClass().getDeclaredMethods();

        for (Method method : wmMethods) {
            if ("isWifiApEnabled".equals(method.getName())) {
                try {
                    result = method.invoke(wifi);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        if (((Object) true).equals(result)) {
            return true;
        }

        return false;
    }

    private boolean isSuccess(retrofit2.Response<?> response){
        return response.code() == 200;
    }

    public boolean isSuccessApp(BaseModel response){
        return response.getResVal() == 1;
    }
}
