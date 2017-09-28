package com.example.wangjinchao_pc.library.enity.baseResult;

/**
 * Created by wangjinchao-PC on 2017/9/22.
 */

public class IdentifyBaseResultEntity<T> {

    private String resStatus;	//状态	int
    private String szErrormsg;	//错误描述	String
    private T objInfo;	//返回个人信息结构	UNIACCOUNT

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public String getSzErrormsg() {
        return szErrormsg;
    }

    public void setSzErrormsg(String szErrormsg) {
        this.szErrormsg = szErrormsg;
    }

    public T getObjInfo() {
        return objInfo;
    }

    public void setObjInfo(T objInfo) {
        this.objInfo = objInfo;
    }
}
