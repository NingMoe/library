package com.example.wangjinchao_pc.library.enity.result;

import com.example.wangjinchao_pc.library.util.SPUtils;

/**
 * Created by wangjinchao-PC on 2017/7/5.
 */

public class AdInfo {
    private static String FILE_NAME="adinfo";

    private static String TEXT="text";
    private static String IMGPATH="imgpath";
    private static String ISPLAYAD="isplayad";
    private static String ISNULL="isnull";

    String text;
    String imgPath;
    boolean isPlayAd=false;
    boolean isNULL=true;//辅助

    public AdInfo(String FILE_NAME, String imgPath, String text) {
        this.FILE_NAME = FILE_NAME;
        this.imgPath = imgPath;
        this.text = text;
        isPlayAd=false;
        isNULL=true;
    }

    public AdInfo() {
        isPlayAd=false;
        isNULL=true;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isPlayAd() {
        return isPlayAd;
    }

    public void setPlayAd(boolean playAd) {
        isPlayAd = playAd;
    }

    public boolean isNULL() {
        return isNULL;
    }

    public void setNULL(boolean NULL) {
        isNULL = NULL;
    }

    public void saveAll(){
        SPUtils.put(FILE_NAME,TEXT,text);
        SPUtils.put(FILE_NAME,IMGPATH,imgPath);
        SPUtils.put(FILE_NAME,ISPLAYAD,isPlayAd);
        SPUtils.put(FILE_NAME,ISNULL,isNULL);
    }

    public void getALL(){
        text=SPUtils.get(FILE_NAME,TEXT,"").toString();
        imgPath=SPUtils.get(FILE_NAME,IMGPATH,"").toString();
        isPlayAd=(boolean)SPUtils.get(FILE_NAME,ISPLAYAD,false);
        isNULL=(boolean)SPUtils.get(FILE_NAME,ISNULL,true);
    }

    public boolean equals(AdInfo adinfo){
        if(isPlayAd==adinfo.isPlayAd&&text.equals(adinfo.getText())&&imgPath.equals(adinfo.getImgPath()))
            return true;
        return false;
    }

}
