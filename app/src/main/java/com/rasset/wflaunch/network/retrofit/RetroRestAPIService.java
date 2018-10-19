package com.rasset.wflaunch.network.retrofit;

import com.rasset.wflaunch.BuildConfig;
import com.rasset.wflaunch.core.TabApp;
import com.rasset.wflaunch.network.protocol.ParamKey;
import com.rasset.wflaunch.utils.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author okc
 * @version 1.0
 * @see
 * @since 2015-12-19.
 */
public class RetroRestAPIService {

    private static final int NETWORK_CONNECT_TIME_OUT = 7;
    private static final int NETWORK_READ_TIME_OUT = 20; // 파일다운없으니 20정도

    private static OkHttpClient.Builder okClient = null;
    private static Retrofit retrofit = null;
    private static Retrofit retrofitPhoto = null;

    public static void initRetrofit(String baseUrl, String fileBaseUrl){
        okClient = new OkHttpClient().newBuilder();
        okClient.connectTimeout(NETWORK_CONNECT_TIME_OUT, TimeUnit.SECONDS);
        okClient.readTimeout(NETWORK_READ_TIME_OUT, TimeUnit.SECONDS);

        if (BuildConfig.LOG_MODE){
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okClient.interceptors().add(logging);
        }
        okClient.networkInterceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request request = chain.request().newBuilder()
                        .removeHeader("Accept-Encoding")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .addHeader(ParamKey.PARAM_USERID, TabApp.Companion.getUserInfo().getUserId()+"")
//                        .addHeader(ParamKey.PARAM_DEVICE_UUIID, AUtil.getDeviceId(SQApp.getContext()) + "")
//                        .addHeader(ParamKey.PARAM_APPVER, AUtil.getVersion())
                        .build();

                String strRequset = chain.request().url().toString();
                if (BuildConfig.LOG_MODE){
                    String strHeader = request.headers().toString();
                    Logger.i("NetManager", "[Request] => " + strRequset + "\n"+ strHeader);
                }
                long startTime = System.nanoTime();
                okhttp3.Response response = chain.proceed(request);
                long endTime = System.nanoTime();
                String bodyString = response.body().string();
                if (BuildConfig.LOG_MODE){
                    Logger.i("NetManager", "[Response] => " + "elapsedTime:( " + ((endTime - startTime) / 1000000) + " ms) " + strRequset);
                    Logger.i("NetManager", "[Response] => " + "body:( "+ bodyString + " )");
                }
                response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), bodyString)).build();
                return response;
            }
        });
//        mExecutorService = Executors.newCachedThreadPool(); Retrofit retrofit = new Retrofit.Builder()
        retrofit = new Retrofit.Builder()
                .client(okClient.build())
                .baseUrl(baseUrl)
//                .callbackExecutor(new MainThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//
//        retrofitPhoto = new Retrofit.Builder()
//                .client(okClient.build())
//                .baseUrl(fileBaseUrl)
////                .callbackExecutor(new MainThreadExecutor())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
    }

    // TODO 서비스 싱글통 필요한가 ?? 오버헤드 체크.
    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

//    public static <S> S createPhotoService(Class<S> serviceClass) {
//        return retrofitPhoto.create(serviceClass);
//    }

}
