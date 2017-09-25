package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class GetBookInfoApi extends BaseApi {
    String book_no;

    public GetBookInfoApi() {
    }

    public GetBookInfoApi(String book_no) {
        this.book_no = book_no;
    }

    void init(){
        //缓存
        setCache(true);
        //取消加载框
        setShowProgress(false);

        setMethod("getBookInfo");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getBookInfo(book_no);
    }
}
