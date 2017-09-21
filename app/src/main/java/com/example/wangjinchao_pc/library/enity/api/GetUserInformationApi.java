package com.example.wangjinchao_pc.library.enity.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/9/21.
 */

public class GetUserInformationApi extends BaseApi {

    String account;

    public GetUserInformationApi() {
        init();
    }

    public GetUserInformationApi(String account) {
        init();
        this.account = account;
    }

    void init(){
        //缓存
        setCache(false);
        //取消加载框
        setShowProgress(false);

        setMethod("GetUserInformationApi");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.getUserInformation(account);
    }

    public void setAllParam(String account){
        this.account=account;
    }
}
