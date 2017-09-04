package com.example.wangjinchao_pc.library.enity.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class ResetPasswordApi extends BaseApi {

    String number;
    String code;
    String password;

    public ResetPasswordApi() {
        init();
    }

    public ResetPasswordApi(String number, String code, String password) {
        init();
        this.number = number;
        this.code = code;
        this.password = password;
    }

    void init(){
        //缓存
        setCache(false);
        //取消加载框
        setShowProgress(false);

        setMethod("resetPassword");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.resetPassword(number,code,password);
    }
}
