package com.example.wangjinchao_pc.library.util;

import android.text.TextUtils;

import com.example.wangjinchao_pc.library.Constant.Constant;

/**
 * Created by wangjinchao-PC on 2017/9/6.
 */

public class Regix {

    private static String regexOfPhone = "^1[0-9]{10}";
    private static String regexOfPassword="^\\w+$";


    public static int isMobile(String number,boolean toastEnable){
        if (number==null||TextUtils.isEmpty(number)) {
            if(toastEnable)
                Utils.showToast("手机号为空");
            return Constant.REGIX_EMPTY;
        } else if(number.matches(regexOfPhone)){
            //matches():字符串是否在给定的正则表达式匹配
           return Constant.REGIX_SUCCESS;
        }else
            return Constant.REGIX_ERROR;
    }

    public static int isPassword(String password, boolean toastEnable){
        if (password==null||TextUtils.isEmpty(password)) {
            if(toastEnable)
                Utils.showToast("密码为空");
            return Constant.REGIX_EMPTY;
        } else if(true){/*password.matches(regexOfPhone)*/
            //matches():字符串是否在给定的正则表达式匹配
            return Constant.REGIX_SUCCESS;
        }else{
            if(toastEnable)
                Utils.showToast("密码格式错误");
            return Constant.REGIX_ERROR;
        }
    }

    public static int isAccount(String account, boolean toastEnable){
        if (account==null||TextUtils.isEmpty(account)) {
            if(toastEnable)
                Utils.showToast("账号为空");
            return Constant.REGIX_EMPTY;
        } else if(true){/*account.matches(regexOfPhone)*/
            //matches():字符串是否在给定的正则表达式匹配
            return Constant.REGIX_SUCCESS;
        }else{
            if(toastEnable)
                Utils.showToast("账号格式错误");
            return Constant.REGIX_ERROR;
        }
    }

    public static int isCode(String code,boolean toastEnable){
        if (code==null||TextUtils.isEmpty(code)) {
            if(toastEnable)
                Utils.showToast("验证码为空");
            return Constant.REGIX_EMPTY;
        } else if(true){/*account.matches(regexOfPhone)*/
            //matches():字符串是否在给定的正则表达式匹配
            return Constant.REGIX_SUCCESS;
        }else{
            if(toastEnable)
                Utils.showToast("验证码格式错误");
            return Constant.REGIX_ERROR;
        }
    }

    public static int isNickname(String nickname,boolean toastEnable){
        if (nickname==null||TextUtils.isEmpty(nickname)) {
            if(toastEnable)
                Utils.showToast("昵称为空");
            return Constant.REGIX_EMPTY;
        } else if(true){/*account.matches(regexOfPhone)*/
            //matches():字符串是否在给定的正则表达式匹配
            return Constant.REGIX_SUCCESS;
        }else{
            if(toastEnable)
                Utils.showToast("昵称格式错误");
            return Constant.REGIX_ERROR;
        }
    }
}
