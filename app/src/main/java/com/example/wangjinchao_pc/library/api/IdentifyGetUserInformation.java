package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.Constant.Configure;
import com.example.wangjinchao_pc.library.httpservice.HttpIdentifyService;
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
        int i=0,j=0;
        for(i=0;i<schoolURL.length();i++){
            if(schoolURL.charAt(i)=='/'){
                if(j<2) j++;
                else break;
            }
        }
        setBaseUrl(schoolURL.substring(0,i+1));
    }
}
