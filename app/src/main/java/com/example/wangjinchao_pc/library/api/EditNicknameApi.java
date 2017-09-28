package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/9/20.
 */

public class EditNicknameApi extends BaseApi {
    String account;
    String nickname;

    public EditNicknameApi() {
        init();
    }

    public EditNicknameApi(String account, String nickname) {
        init();
        this.account = account;
        this.nickname = nickname;
    }

    void init(){
        //缓存
        setCache(false);
        setShowProgress(true);
        setCancel(true);

        setMethod("EditNicknameApi");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.editNickname(account,nickname);
    }

    public void setAllParam(String account, String nickname) {
        this.account = account;
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
