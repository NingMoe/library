package com.example.wangjinchao_pc.library.httpservice;

import org.greenrobot.greendao.annotation.OrderBy;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wangjinchao-PC on 2017/7/30.
 */

public interface HttpService {

    //获取广告
    @GET("school/getAd_url")
    Observable<String>getAdvertisement(@Query("school_id") int school_id);
    //登录
    @GET("user/login")
    Observable<String>login(@Query("account")String account,@Query("password")String password);
    //获取详细信息
    @GET("user/getOneUser")
    Observable<String>getUserInformation(@Query("account")String account);
    //设置新密码
    @GET("password/editpassword")
    Observable<String>resetPassword(@Query("account") String account,@Query("code") String code,@Query("password") String newPassword,@Query("oldpassword")String nowPassword);
    //获取验证码—找回密码
    @GET("password/sendmsg")
    Observable<String>getPasswordCode(@Query("phone") String number);
    //注册账号
    @GET("register/registuser")
    Observable<String>register(@Query("account")String number,@Query("password")String password,@Query("nickname")String nickname,@Query("code")String code);
    //获取验证码—注册
    @GET("register/sendmsg")
    Observable<String>getRegisterCode(@Query("phone") String number);
    //获取banner信息
    @GET("getBanner")
    Observable<String>getBanner();
    //获取预约信息
    @GET("getOrder/{token}")
    Observable<String>getOrder(@Query("token")String token);
    //获取排名信息
    @GET("raking/getRakingList")
    Observable<String>getRank(@Query("year")String year,@Query("month")String month,@Query("account")String account);
    //获取未还书籍数量
    @GET("getDueBookNumber/{token}")
    Observable<String>getDueBookNumber(@Query("token")String token);
    //获取荐购书籍信息
    @GET("getRecommendBook/{page}")
    Observable<String>getRecommendBook(@Query("page")int page);
    //获取相关书籍信息
    @GET("getAboutBook/{page}")
    Observable<String>getAboutBook(@Query("page")int page);
    //获取好评书籍信息
    @GET("getGoodBook/{page}")
    Observable<String>getGoodBook(@Query("page")int page);
    //获取热门书籍信息
    @GET("getRedBook/{page}")
    Observable<String>getRedBook(@Query("page")int page);
    //获取新书书籍信息
    @GET("getNewBook/{page}")
    Observable<String>getNewBook(@Query("page")int page);
    //收藏书籍操作
    @GET("collect/{token},{bookNo},{isCollect}")
    Observable<String>collect(@Query("token")String token,@Query("bookNo")String bookNo,@Query("isCollect")boolean isCollect);
    //获取书籍详细信息
    @GET("getBookInfo/{bookNo}")
    Observable<String>getBookInfo(@Query("bookNo")String bookNo);
    //获取书籍评论
    @GET("getBookComment/{bookNo},{page}")
    Observable<String>getBookComment(@Query("bookNo")String bookNo,@Query("page")int page);
    //点赞书籍操作
    @GET("zan/{token},{bookNo},{isZan}")
    Observable<String>zan(@Query("token")String token,@Query("bookNo")String bookNo,@Query("isZan")boolean isZan);
    //评论书籍
    @GET("comment/{token},{content}")
    Observable<String>commend(@Query("token")String token,@Query("content")String content);
    //欠款查询
    @GET("arrears/getArrearsList")
    Observable<String>inqueryDuePrice(@Query("account")String account);
    //未还书籍查询
    @GET("arrears/getBooksList")
    Observable<String>inqueryDueBook(@Query("account")String account);
    //获取通知
    @GET("getNotice/{token}")
    Observable<String>getNotice(@Query("token")String token);
    //修改信息
    //
    //
    //

    //获取学校列表
    @GET("getCollege/{page}")
    Observable<String>getCollege(@Query("page")int page);
    //绑定学校
    @GET("bindCollege/{token},{college},{no},{password}")
    Observable<String>bindCollege(@Query("token")String token,@Query("college")String college,@Query("no")String no,@Query("password")String password);
    //绑定个人信息
    @GET("ident/addIdent")
    Observable<String>bindCollegeInfo(@Query("account")String account,@Query("schoolName")String schoolName,@Query("studentid")String studentid,@Query("trueName")String trueName,
                                      @Query("collegeName")String collegeName,@Query("majorName")String majorName,@Query("sex")String sex,@Query("ident")String ident,@Query("enrolYear")String enrolYear,@Query("orderUrl")String orderUrl);
    //获取预约界面
    @GET("ident/getOneIdent")
    Observable<String>getOrderUrl(@Query("account")String account);
    //绑定后修改信息——信息不完全
    @GET("modifyInformation/{token},{sex}")
    Observable<String>modifyInformation(@Query("token")String token,@Query("sex")String sex);
    //密码修改
    @GET("password/updatepassword")
    Observable<String>modifyPassword(@Query("account")String account,@Query("password")String password,@Query("oldpassword")String oldpassword);
    //退出登录
    @GET("logout/{token}")
    Observable<String>logout(@Query("token")String token);
    //修改信息
    @GET("user/editUser")
    Observable<String>editInformation(@Query("account")String account,@Query("password")String password,@Query("name")String name,
                                      @Query("hobbyid")String hobbyid,@Query("sex")String sex,@Query("photourl")String photourl,
                                      @Query("nickname")String nickname);
    //修改昵称
    @GET("user/editNickname")
    Observable<String>editNickname(@Query("account")String account, @Query("nickname")String nickname);
    //修改性别
    @GET("user/editSex")
    Observable<String>editSex(@Query("account")String account,@Query("sex")String sex);
    //修改信息
    @GET("user/editHobbyid")
    Observable<String>editHobbyid(@Query("account")String account,@Query("hobbyid")String hobbyid);
    //修改头像
    @Multipart
    @POST("/SmartLibrary/user/editPhotourl")
    Observable<String>uploadPhoteImg(@Part("account") RequestBody account, @Part("photourl") RequestBody  photourl, @Part MultipartBody.Part uplodThings);
    //修改信息
    @GET("hobby/getHobbyList")
    Observable<String>getHobbyList();
}
