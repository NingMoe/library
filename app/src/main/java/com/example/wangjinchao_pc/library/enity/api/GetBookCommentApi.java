package com.example.wangjinchao_pc.library.enity.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class GetBookCommentApi extends BaseApi {
    String book_no;
    int page;

    public GetBookCommentApi() {
    }

    public GetBookCommentApi(String book_no, int page) {
        this.book_no = book_no;
        this.page = page;
    }

    void init(){
        //缓存
        setCache(true);
        //取消加载框
        setShowProgress(false);

        setMethod("getBookComment");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getBookComment(book_no,page);
    }
}
