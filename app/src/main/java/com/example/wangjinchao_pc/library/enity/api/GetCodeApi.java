package com.example.wangjinchao_pc.library.enity.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class GetCodeApi extends BaseApi {

    String number;

    public GetCodeApi() {
        init();
    }

    public GetCodeApi(String number) {
        init();
        this.number = number;
    }

    void init(){
        //缓存
        setCache(false);
        //取消加载框
        setShowProgress(false);

        setMethod("getCode");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getCode(number);
    }
}
