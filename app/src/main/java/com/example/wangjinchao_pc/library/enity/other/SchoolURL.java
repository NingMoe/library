package com.example.wangjinchao_pc.library.enity.other;

import java.io.Serializable;

/**
 * Created by wangjinchao-PC on 2017/9/22.
 */

public class SchoolURL implements Serializable{
    String url;
    String schoolCode;
    String schoolImg;
    String schoolName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolImg() {
        return schoolImg;
    }

    public void setSchoolImg(String schoolImg) {
        this.schoolImg = schoolImg;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return schoolName;
    }
}
