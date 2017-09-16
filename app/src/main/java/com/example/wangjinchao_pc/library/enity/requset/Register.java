package com.example.wangjinchao_pc.library.enity.requset;

/**
 * Created by wangjinchao-PC on 2017/9/16.
 */

public class Register {
    private String mobile;
    private String password;
    private String nickname;
    private String certify;

    public Register(String mobile, String password, String nickname, String certify) {
        this.mobile = mobile;
        this.password = password;
        this.nickname = nickname;
        this.certify = certify;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCertify() {
        return certify;
    }

    public void setCertify(String certify) {
        this.certify = certify;
    }
}
