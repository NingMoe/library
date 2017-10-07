package com.example.wangjinchao_pc.library.api;

import com.example.wangjinchao_pc.library.enity.mine.BindParam;
import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/10/7.
 */
public class BindCollegeInfoApi extends BaseApi {
    BindParam bindParam;

    public BindCollegeInfoApi() {
        init();
    }

    void init(){
        //缓存
        setCache(false);
        setShowProgress(true);
        setCancel(false);

        setMethod("BindCollegeInfoApi");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        return httpService.bindCollegeInfo(bindParam.getAccount(),bindParam.getSchoolName(),bindParam.getStudentid(),bindParam.getTrueName(),bindParam.getCollegeName(),
                bindParam.getMajorName(),bindParam.getSex(),bindParam.getIdent(),bindParam.getEnrolYear(),bindParam.getOrderUrl());
    }

    public void setAllParam(BindParam bindParam) {
        this.bindParam=bindParam;
    }
}
