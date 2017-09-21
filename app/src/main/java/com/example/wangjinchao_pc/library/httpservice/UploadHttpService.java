package com.example.wangjinchao_pc.library.httpservice;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by wangjinchao-PC on 2017/9/19.
 */

public interface UploadHttpService {
    @Multipart
    @POST("/user/editPhotourl")
    Call uploadPhoteImg(@Query("account")String account,@Query("photourl") String photoyrl, @Part MultipartBody.Part uplodThings);
}
