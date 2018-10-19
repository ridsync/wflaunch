package com.rasset.wflaunch.network.retrofit;

import com.rasset.wflaunch.network.res.BaseModel;
import com.rasset.wflaunch.network.res.ResContentList;
import com.rasset.wflaunch.network.res.ResCustomerList;
import com.rasset.wflaunch.network.res.ResDiagnoseInfo;
import com.rasset.wflaunch.network.res.ResUserLogin;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by andman on 2015-12-24.
 */
public interface MainAPInterface {

    @POST("userLogin")
    Call<ResUserLogin> reqPostUserLogin(@Body Map<String, Object> params);

    @GET("internListCustomers/{userId}")
    Call<ResCustomerList> reqGetDiagnoseList(@Path("userId") String userId);

    @Headers("Content-Type: application/json")
    @POST("updateDiagInfos")
    Call<BaseModel>  reqPostDiagnoseUpdate(@Body Map<String, Object> params);

}
