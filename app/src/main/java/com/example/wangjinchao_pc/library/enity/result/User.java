package com.example.wangjinchao_pc.library.enity.result;

import com.example.wangjinchao_pc.library.util.SPUtils;

/**
 * Created by wangjinchao-PC on 2017/7/5.
 */

public class User {

    private static String FILE_NAME="user";
    private static String DEFAULT_VALUE = "";

    private static String USERID = "userid";
    private static String USERNAME = "username";
    private static String PASSWORD = "password";

    private static String IS_LOGIN = "islogin";

    private static String SESSION_ID = "session_id";

    public static String get(String key){
        return SPUtils.get(FILE_NAME, key, DEFAULT_VALUE).toString();
    }

    public static void put(String key,String value){
        SPUtils.put(FILE_NAME,key,value);
    }

    public static String getDefaultValue() {
        return DEFAULT_VALUE;
    }

    public static void setDefaultValue(String defaultValue) {
        DEFAULT_VALUE = defaultValue;
    }

    public static boolean getIsLogin() {
        return (boolean)SPUtils.get(FILE_NAME, IS_LOGIN, false);
    }

    public static void setIsLogin(boolean isLogin) {
        SPUtils.put(FILE_NAME,IS_LOGIN,isLogin);
    }

    public static String getPassword() {
        return get(PASSWORD);
    }

    public static void setPassword(String Password) {
        put(PASSWORD,Password);
    }

    public static String getSessionId() {
        return get(SESSION_ID);
    }

    public static void setSessionId(String sessionId) {
        put(PASSWORD,sessionId);
    }


    public static String getUserId() {
        return get(USERID);
    }

    public static void setUserId(String userid) {
        put(USERID,userid);
    }

    public static String getUserName() {
        return get(USERNAME);
    }

    public static void setUserName(String username) {
        put(USERNAME,username);
    }

//    public static List<Shelf> getShelf(){
//        String json = getShared().getString(SHELF, DEFAULT_VALUE);
//        if(!json.equals(DEFAULT_VALUE)){
//            Log.e("getshelf",json);
//            return JSON.parseArray(json, Shelf.class);
//        }else{
//            return null;
//        }
//    }
}
