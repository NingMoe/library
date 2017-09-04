package com.example.wangjinchao_pc.library.enity.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class LoginApi extends BaseApi {
    String name;
    String password;

    public LoginApi() {
        init();
    }

    public LoginApi(String name, String password) {
        init();
        this.name = name;
        this.password = password;
    }

    void init(){
        //缓存
        setCache(false);
        //取消加载框
        setShowProgress(false);

        setMethod("login");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.login(name,password);
    }
}
