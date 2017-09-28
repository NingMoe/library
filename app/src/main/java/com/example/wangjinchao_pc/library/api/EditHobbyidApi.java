package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/9/20.
 */

public class EditHobbyidApi extends BaseApi {
    String account;
    String hobbyid;
    String name;

    public EditHobbyidApi() {
        init();
    }

    public EditHobbyidApi(String account, String hobbyid,String name) {
        init();
        this.account = account;
        this.hobbyid = hobbyid;
        this.name=name;
    }

    void init(){
        //缓存
        setCache(false);
        setShowProgress(true);
        setCancel(true);

        setMethod("EditHobbyidApi");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.editHobbyid(account,hobbyid);
    }

    public void setAllParam(String account, String hobbyid,String name) {
        this.account = account;
        this.hobbyid = hobbyid;
        this.name=name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getHobbyid() {
        return hobbyid;
    }

    public void setHobbyid(String hobbyid) {
        this.hobbyid = hobbyid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
