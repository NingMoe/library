package com.example.wangjinchao_pc.library.httpservice;

import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/9/22.
 */

public interface HttpIdentifyService {
    //获取同一认证学校列表
    @GET("unialipay/schoollist.aspx")
    Observable<String> getSchoolList();

    //获得相应学校的学生信息
    /*@GET("{SchoolURL}/appInterface/login.aspx")
    Observable<String> getUserInformation(@Path("SchoolURL")String schoolURL, @Query("logonname")String logonname, @Query("password")String password);*/
    @GET("appInterface/login.aspx")
    Observable<String> getUserInformation(@Query("logonname")String logonname, @Query("password")String password);

}
