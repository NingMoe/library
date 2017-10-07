package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/10/7.
 */

public class ModifyPasswordApi extends BaseApi {
    String account;
    String password;
    String oldpassword;

    public ModifyPasswordApi() {
        init();
    }

    public ModifyPasswordApi(String account, String password, String oldpassword) {
        init();
        this.account = account;
        this.password = password;
        this.oldpassword = oldpassword;
    }

    void init(){
        //缓存
        setCache(false);
        setShowProgress(true);
        setCancel(false);

        setMethod("ModifyPasswordApi");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.modifyPassword(account,password,oldpassword);
    }

    public void setAllParam(String account, String password, String oldpassword) {
        this.account = account;
        this.password = password;
        this.oldpassword = oldpassword;
    }
}
