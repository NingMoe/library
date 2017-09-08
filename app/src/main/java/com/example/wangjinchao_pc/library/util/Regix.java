package com.example.wangjinchao_pc.library.util;

import android.text.TextUtils;

/**
 * Created by wangjinchao-PC on 2017/9/6.
 */

public class Regix {

    private static String regexOfPhone = "^1[0-9]{10}";
    private static String regexOfPassword="^\\w+$";


    public static boolean isMobile(String number,boolean toastEnable){
        if (TextUtils.isEmpty(number.trim())) {
            if(toastEnable)
                Utils.showToast("手机号为空");
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(regexOfPhone);
        }
    }

    public static boolean isPassword(String password, boolean toastEnable){
        if (TextUtils.isEmpty(password.trim())) {
            if(toastEnable)
                Utils.showToast("密码为空");
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            /*return password.matches(regexOfPhone);*/
            return true;
        }
    }

    public static boolean isAccount(String account, boolean toastEnable){
        return true;
    }
}
