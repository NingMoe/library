package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public class RegisterApi extends BaseApi {
    String number;
    String password;
    String nickname;
    String code;

    public RegisterApi() {
        init();
    }

    public RegisterApi(String number, String password, String nickname, String code) {
        init();
        this.number = number;
        this.password = password;
        this.nickname = nickname;
        this.code = code;
    }

    void init(){
        //缓存
        setCache(false);
        setShowProgress(true);
        setCancel(false);

        setMethod("resetPassword");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.register(number,password,nickname,code);
    }

    public void setAllParam(String number, String password, String nickname, String code) {
        this.number = number;
        this.password = password;
        this.nickname = nickname;
        this.code = code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
