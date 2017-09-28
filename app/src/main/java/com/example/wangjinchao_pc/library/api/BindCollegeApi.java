package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class BindCollegeApi extends BaseApi {
    String token;
    String college;
    String no;
    String password;

    public BindCollegeApi() {
    }

    public BindCollegeApi(String token, String college, String no, String password) {
        this.token = token;
        this.college = college;
        this.no = no;
        this.password = password;
    }

    void init(){
        //缓存
        setCache(true);
        //取消加载框
        setShowProgress(false);

        setMethod("bindCollege");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.bindCollege(token,college,no,password);
    }
}
