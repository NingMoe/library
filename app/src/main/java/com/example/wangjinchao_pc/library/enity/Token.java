package com.example.wangjinchao_pc.library.enity;

/**
 * Created by wangjinchao-PC on 2017/9/7.
 */

public class Token {
    private String account;
    private String password;

    public Token() {
    }

    public Token(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEmpty(){
        if(account.equals("")||password.equals(""))
            return true;
        else
            return false;
    }
}
