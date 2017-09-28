package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class SetPasswordApi extends BaseApi {
    String token;
    String nowPassword;
    String newPassword;

    public SetPasswordApi() {
        init();
    }

    public SetPasswordApi(String token, String nowPassword, String newPassword) {
        init();
        this.token = token;
        this.nowPassword = nowPassword;
        this.newPassword = newPassword;
    }

    void init(){
        //缓存
        setCache(true);
        //取消加载框
        setShowProgress(false);

        setMethod("setPassword");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.setPassword(token,nowPassword,newPassword);
    }
}
