package com.example.wangjinchao_pc.library.enity.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/9/20.
 */

public class EditSexApi extends BaseApi{
    String account;
    String sex;

    public EditSexApi() {
        init();
    }

    public EditSexApi(String account, String sex) {
        init();
        this.account = account;
        this.sex = sex;
    }

    void init(){
        //缓存
        setCache(false);
        setShowProgress(true);
        setCancel(true);

        setMethod("EditSexApi");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.editSex(account,sex);
    }

    public void setAllParam(String account, String sex) {
        this.account = account;
        this.sex = sex;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
