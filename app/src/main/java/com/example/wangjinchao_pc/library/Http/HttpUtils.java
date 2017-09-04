package com.example.wangjinchao_pc.library.Http;

import android.util.Log;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.util.Utils;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by hongzhiyuanzj on 2017/4/24.
 */
public class HttpUtils {

//    public static void getSession(final SessionCallback secall){
//        login(Prefer.getUserId(), Prefer.getPassword(), new HttpCallback() {
//            @Override
//            public void onSuccess(String json) {
//                Result result = JSON.parseObject(json, Result.class);
//                if(result.getCode()==0){
//                    Prefer.saveSessionId(result.getSessionId());
//                    secall.callback();
//                }
//                if(result.getCode()==1){
//
//                }
//            }
//        });
//    }

    public static void getAd_Info(HttpCallback callback){
        OKHttpHelper.getInstance().postAsycHttp(AppURL.AdInfo, null,callback);
    }

    public static void loginToWeb(String id, String userid, String password, HttpCallback callback){
        Map<String,String> map = new HashMap<>();
        map.put("t", "appscan");
        map.put("id", id);
        Log.e("id", id);
        map.put("userId",userid);
        map.put("password",password);
        OKHttpHelper.getInstance().postAsycHttp(AppURL.LoginToWeb, map,callback);
    }



//    public static void login(String username, String password, HttpCallback callback){
//        String timestamp = System.currentTimeMillis()+"";
//        String sign = MD5.stringToMD5(Values.sysid+Values.syscode+timestamp);
//        Map<String,String> map = new HashMap<>();
//        map.put("t", "app");
//        map.put("userId", username);
//        map.put("sysid", Values.sysid);
//        map.put("syscode", Values.syscode);
//        map.put("password", password);
//        map.put("timestamp", timestamp);
//        map.put("sign", sign);
//        OKHttpHelper.getInstance().postAsycHttp(AppURL.Login, map, callback);
//    }
//
//   public static void getCertifyCode(String mobile, HttpCallback callback){
//       Map<String,String> map = new HashMap<>();
//       map.put("mobile", mobile);
//       OKHttpHelper.getInstance().postAsycHttp(AppURL.getCertifyCode, map, callback);
//   }

//    public static void register(String smscode, String mobile, String name, String password, HttpCallback callback){
//        Map<String,String> map = new HashMap<>();
//        map.put("mobile", mobile);
//        map.put("smscode", smscode);
//        map.put(Values.sessionId,Prefer.getSeesionId());
//        map.put("name", name);
//        map.put("password", password);
//        OKHttpHelper.getInstance().postAsycHttp(AppURL.register, map, callback);
//    }

//    public static void  getUserInfo(String openId, final HttpCallback callback){
//        Map<String,String> map = new HashMap<>();
//        map.put("openid", openId);
//        map.put(Values.sessionId, Prefer.getSeesionId());
//        OKHttpHelper.getInstance().postAsycHttp(AppURL.getUserInfo, map, callback);
//
//    }

//    public static void getBookList(int page, int needlines, String keywords, HttpCallback callback){
//        Map<String,String> map = new HashMap<>();
//        map.put("keyword", keywords);
//        map.put("page",page+"");
//        map.put("needlines", needlines+"");
//        map.put("mode","jsonlist");
//        OKHttpHelper.getInstance().postAsycHttp(AppURL.getBookList, map, callback);
//
//    }

//    public static void getBookList(int page, String keywords, HttpCallback callback){
//        getBookList(page, Values.page_number, keywords, callback);
//    }

//    public static void getBookInfo(String id, HttpCallback callback){
//        Map<String,String> map = new HashMap<>();
//        map.put("id", id);
//        OKHttpHelper.getInstance().postAsycHttp(AppURL.getBookDetail, map, callback);
//    }


//    public static void getTalkList(final String bookid, final int page, final int needlines, final HttpCallback callback){
//        if(Prefer.isLogin()){
//            getSession(new SessionCallback() {
//                @Override
//                public void callback() {
//                    Map<String,String> map = new HashMap<>();
//                    map.put("id", bookid);
//                    map.put("page", page+"");
//                    map.put("needlines", needlines+"");
//
//                    map.put(Values.sessionId, Prefer.getSeesionId());
//
//                    OKHttpHelper.getInstance().postAsycHttp(AppURL.getTalkList, map, callback);
//                }
//            });
//        }else{
//            Map<String,String> map = new HashMap<>();
//            map.put("id", bookid);
//            map.put("page", page+"");
//            map.put("needlines", needlines+"");
//            OKHttpHelper.getInstance().postAsycHttp(AppURL.getTalkList, map, callback);
//        }
//
//    }

//    public static void getTalkList(String bookid, int page, HttpCallback callback){
//        getTalkList(bookid, page, Values.page_number, callback);
//    }


//    public static void commitTalk(final String bookid, final int star, final String comment, final HttpCallback callback){
//
//        getSession(new SessionCallback() {
//            @Override
//            public void callback() {
//                Map<String,String> map = new HashMap<>();
//                map.put("bookId", bookid);
//                map.put("rating", star+"");
//                map.put("content", comment);
//                map.put(Values.sessionId, Prefer.getSeesionId());
//                OKHttpHelper.getInstance().postAsycHttp(AppURL.commitTalk, map, callback);
//            }
//        });
//
//    }

//    public static void getRecommendDirList(final HttpCallback callback){
//        if(Prefer.isLogin()){
//            getSession(new SessionCallback() {
//                @Override
//                public void callback() {
//                    Map<String,String> map = new HashMap<>();
//                    map.put(Values.sessionId, Prefer.getSeesionId());
//                    OKHttpHelper.getInstance().postAsycHttp(AppURL.getRecommendDir, map, callback);
//                }
//            });
//        }else{
//            Map<String,String> map = new HashMap<>();
//            OKHttpHelper.getInstance().postAsycHttp(AppURL.getRecommendDir, map, callback);
//        }
//
//    }

//    public static void getRecommendList(int rdid, int page,  HttpCallback callback){
//        Map<String,String> map = new HashMap<>();
//        map.put("rdId", rdid+"");
//        map.put("page", page+"");
//        map.put("needlines",Values.page_number+"");
//        OKHttpHelper.getInstance().postAsycHttp(AppURL.getRecommendList, map, callback);
//    }

//    public static void recommend(final int rdid, final HttpCallback callback){
//        getSession(new SessionCallback() {
//            @Override
//            public void callback() {
//                Map<String,String> map = new HashMap<>();
//                map.put("rdId", rdid+"");
//                map.put(Values.sessionId, Prefer.getSeesionId());
//                OKHttpHelper.getInstance().postAsycHttp(AppURL.recommend, map, callback);
//            }
//        });
//
//    }
//
//    public static void getIdentityList(final HttpCallback callback){
//        getSession(new SessionCallback() {
//            @Override
//            public void callback() {
//                Map<String,String> map = new HashMap<>();
//                map.put(Values.sessionId, Prefer.getSeesionId());
//                OKHttpHelper.getInstance().postAsycHttp(AppURL.getIdenList, map, callback);
//            }
//        });
//
//    }

//    public static void getLibraryList(String para, HttpCallback callback){
//        Map<String,String> map = new HashMap<>();
//        map.put("libName", para);
//        OKHttpHelper.getInstance().postAsycHttp(AppURL.getLibraryList, map, callback);
//    }

//    public static void identify(final int id, final int type, final String account, final String password, final HttpCallback callback){
//        getSession(new SessionCallback() {
//            @Override
//            public void callback() {
//                Map<String,String> map = new HashMap<>();
//                map.put("library", id+"");
//                map.put("libType", type+"");
//                map.put("account", account);
//                map.put("password", password);
//                map.put(Values.sessionId, Prefer.getSeesionId());
//                OKHttpHelper.getInstance().postAsycHttp(AppURL.identify, map, callback);
//            }
//        });
//
//    }

//    public static void zanForComment(final long commentId, final HttpCallback callback){
//
//        getSession(new SessionCallback() {
//            @Override
//            public void callback() {
//                Map<String,String> map = new HashMap<>();
//                map.put("commentId", commentId+"");
//                map.put(Values.sessionId, Prefer.getSeesionId());
//                OKHttpHelper.getInstance().postAsycHttp(AppURL.zanForComment, map, callback);
//            }
//        });
//
//
//    }

//    public static void isExistMobile(String mobile, HttpCallback callback){
//        Map<String,String> map = new HashMap<>();
//        map.put("mobile", mobile);
//        OKHttpHelper.getInstance().postAsycHttp(AppURL.isExistMobile, map, callback);
//    }


//    public static void getMessageList(int page, HttpCallback callback){
//        Map<String,String> map = new HashMap<>();
//        map.put("Page.dwStartLine", page+"");
//        map.put("Page.dwNeedLines",Values.page_number+"");
//        map.put("userTypeId",0+"");
//        OKHttpHelper.getInstance().postAsycHttp(AppURL.isExistMobile, map, callback);
//
//    }

    public static abstract class HttpCallback{
        public void onSuccess(String json){};
        public void onFailure(){
            Utils.showToast(R.string.net_fail);};
    }

    public interface SessionCallback{
        public void callback();

    }
}
