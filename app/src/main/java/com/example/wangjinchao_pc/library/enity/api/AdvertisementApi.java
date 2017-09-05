package com.example.wangjinchao_pc.library.enity.api;

import com.example.wangjinchao_pc.library.httpservice.HttpPostService;
import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class AdvertisementApi extends BaseApi {

    public AdvertisementApi() {
        //缓存
        setCache(true);
        //取消加载框
        setShowProgress(false);

        setMethod("advertisement");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getAdvertisement(2);
    }
}
