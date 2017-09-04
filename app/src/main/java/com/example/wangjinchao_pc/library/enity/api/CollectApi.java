package com.example.wangjinchao_pc.library.enity.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class CollectApi extends BaseApi {
    String token;
    String book_no;
    boolean isCollect;

    public CollectApi() {
        init();
    }

    public CollectApi(String token, String book_no, boolean isCollect) {
        init();
        this.token = token;
        this.book_no = book_no;
        this.isCollect = isCollect;
    }

    void init(){
        //缓存
        setCache(true);
        //取消加载框
        setShowProgress(false);

        setMethod("collect");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.collect(token,book_no,isCollect);
    }
}
