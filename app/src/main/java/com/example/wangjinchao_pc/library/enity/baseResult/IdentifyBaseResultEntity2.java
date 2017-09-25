package com.example.wangjinchao_pc.library.enity.baseResult;

/**
 * Created by wangjinchao-PC on 2017/9/22.
 */

public class IdentifyBaseResultEntity2<T> {
    private T result;  //针对获取学校列表的返回结果

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
