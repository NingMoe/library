package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class InqueryDuePriceApi extends BaseApi {
    String token;

    String account;

    public InqueryDuePriceApi() {
        init();
    }

    public InqueryDuePriceApi(String account) {
        init();
        this.account = account;
    }

    void init(){
        //缓存
        setCache(true);
        //取消加载框
        setShowProgress(false);

        setMethod("inqueryDuePrice");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.inqueryDuePrice(account);
    }

    public void setAllParam(String account){
        this.account=account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
