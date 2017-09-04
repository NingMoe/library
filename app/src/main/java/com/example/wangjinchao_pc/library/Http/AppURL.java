package com.example.wangjinchao_pc.library.Http;

/**
 * Created by hongzhiyuanzj on 2017/4/24.
 */
public class AppURL {
    public static String URL = "http://ercms.iegreen.net/";
//    public static String URL = "http://122.224.73.226:8081/";

    public static String AdInfo=URL+"??????";//获取广告信息
    public static String LoginToWeb  = URL+"auth/Login"; //扫码登录
    public static String getCertifyCode = URL+"auth/SMSCode";  //获取短信验证码
    public static String Login = URL+"auth/Login";  //登录
    public static String register = URL+"auth/Regist"; //注册
    public static String getUserInfo = URL+"client/UserInfo"; //获取用户信息
    public static String getBookList = URL+"opac/search"; //书籍搜索
    public static String getBookDetail = URL+"opac/bookinfo"; // 书籍详情
    public static String getTalkList = URL+"comment/Get";  //获取评论列表
    public static String commitTalk = URL+"comment/Set";  //发表评论

    public static String getRecommendDir = URL+"client/GetRecommendDir"; //获取荐购列表
    public static String getRecommendList = URL+"client/GetRecommendDirBookList"; //获取推荐目录书目列表
    public static String recommend = URL+"client/RecommendDir"; //推荐目录

    public static String getIdenList = URL+"auth/GetIdent"; //获取身份列表
    public static String getLibraryList = URL+"auth/GetLibList"; //获取图书馆列表
    public static String identify = URL +"auth/AppIdent"; //身份绑定申请

    public static String zanForComment = URL + "comment/Vote";   //给评论点赞

    public static String isExistMobile = URL +"auth/IsExistMobile"; //手机是否已存在

}
