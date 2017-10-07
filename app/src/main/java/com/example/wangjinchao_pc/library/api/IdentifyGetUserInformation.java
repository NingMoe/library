package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.Constant.Configure;
import com.example.wangjinchao_pc.library.httpservice.HttpIdentifyService;
import com.example.wangjinchao_pc.library.util.Utils;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/9/22.
 */

public class IdentifyGetUserInformation extends BaseApi{
    String logonname;
    String password;
    String schoolURL;

    public IdentifyGetUserInformation() {
        init();
    }

    public IdentifyGetUserInformation(String logonname, String password, String schoolURL) {
        this.logonname = logonname;
        this.password = password;
        this.schoolURL = schoolURL;
        init();
    }

    void init(){

        setCache(false);
        setShowProgress(true);
        setCancel(false);

        setMethod("IdentifyGetUserInformation");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpIdentifyService httpIdentifyService = retrofit.create(HttpIdentifyService.class);
        return httpIdentifyService.getUserInformation(logonname,password);
    }

    public void setAllParam(String logonname, String password, String schoolURL){
        this.logonname = logonname;
        this.password = password;
        this.schoolURL = schoolURL;
        setBaseUrl(Utils.getPrefixUrl(schoolURL));
    }
}
