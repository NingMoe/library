package com.example.wangjinchao_pc.library.api;


import com.example.wangjinchao_pc.library.httpservice.HttpService;
import com.retrofit_rx.Api.BaseApi;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/9/19.
 */

public class UploadPhoteImgApi extends BaseApi {
    String account;
    String photourl;
    String path;

    public UploadPhoteImgApi() {
        init();
    }

    void init(){
        //缓存
        setCache(false);

        setShowProgress(true);
        setCancel(false);

        setMethod("UploadPhoteImgApi");
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpService httpService = retrofit.create(HttpService.class);
        File file=new File(path);
        RequestBody requestBody=RequestBody.create(MediaType.parse("image/jpg"),file);
        MultipartBody.Part body=MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        RequestBody myaccount = RequestBody.create(MediaType.parse("multipart/form-data"), account);
        RequestBody myphotourl = RequestBody.create(MediaType.parse("multipart/form-data"), photourl);
        return httpService.uploadPhoteImg(myaccount,myphotourl, body);
    }

    public void setAllParam(String account,String photourl,String path){
        this.account=account;
        this.photourl=photourl;
        this.path=path;
    }
}
