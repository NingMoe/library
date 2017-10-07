package com.example.wangjinchao_pc.library.enity.mine;

import java.io.Serializable;

/**
 * Created by wangjinchao-PC on 2017/10/7.
 */

public class BindParam implements Serializable {
    String account;
    String schoolName;
    String studentid;
    String trueName;
    String collegeName;
    String majorName;
    String sex;
    String ident;
    String enrolYear;
    String orderUrl;

    public BindParam(String account, String schoolName, String studentid, String trueName, String collegeName, String majorName, String sex, String ident, String enrolYear, String orderUrl) {
        this.account = account;
        this.schoolName = schoolName;
        this.studentid = studentid;
        this.trueName = trueName;
        this.collegeName = collegeName;
        this.majorName = majorName;
        this.sex = sex;
        this.ident = ident;
        this.enrolYear = enrolYear;
        this.orderUrl = orderUrl;
    }

    public BindParam() {
    }

    public void setAllParam(String account, String schoolName, String studentid, String trueName, String collegeName, String majorName, String sex, String ident, String enrolYear, String orderUrl){
        this.account = account;
        this.schoolName = schoolName;
        this.studentid = studentid;
        this.trueName = trueName;
        this.collegeName = collegeName;
        this.majorName = majorName;
        this.sex = sex;
        this.ident = ident;
        this.enrolYear = enrolYear;
        this.orderUrl = orderUrl;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getEnrolYear() {
        return enrolYear;
    }

    public void setEnrolYear(String enrolYear) {
        this.enrolYear = enrolYear;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }


}
