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
    String newPassword;
    String nowPassword;

    public ResetPasswordApi() {
        init();
    }

    public ResetPasswordApi(String number, String code, String newPassword, String nowPassword) {
        init();
        this.number = number;
        this.code = code;
        this.newPassword = newPassword;
        this.nowPassword=nowPassword;
    }

    void init(){
        //缓存
        setCache(false);
        setShowProgress(true);
        setCancel(false);

        setMethod("resetPassword");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.resetPassword(number,code,newPassword,nowPassword);
    }

    public void setAllParam(String number, String code, String newPassword, String nowPassword) {
        this.number = number;
        this.code = code;
        this.newPassword = newPassword;
        this.nowPassword=nowPassword;
    }
}
