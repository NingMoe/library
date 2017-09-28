package com.example.wangjinchao_pc.library.enity.baseResult;

/**
 * Created by wangjinchao-PC on 2017/9/15.
 */

public class BaseResultEntity2<T>{
    //  判断标示
    private int result;
    //    提示信息
    private String err_msg;
    //显示数据（用户需要关心的数据）
    private T data;

    private String code;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
