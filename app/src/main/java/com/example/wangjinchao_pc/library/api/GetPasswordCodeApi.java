package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class GetPasswordCodeApi extends BaseApi {

    String number;

    public GetPasswordCodeApi() {
        init();
    }

    public GetPasswordCodeApi(String number) {
        init();
        this.number = number;
    }

    void init(){
        //缓存
        setCache(false);
        //取消加载框
        setShowProgress(false);

        setMethod("getPasswordCode");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getPasswordCode(number);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAllParam(String number) {
        this.number = number;
    }
}
