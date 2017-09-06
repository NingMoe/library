package com.example.wangjinchao_pc.library;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.enity.api.ResetPasswordApi;
import com.example.wangjinchao_pc.library.enity.result.BaseResultEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangjinchao-PC on 2017/9/5.
 */

public class Test {

    public static void main(String[] args) {

        String mobiles="17816877003";
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "—");
        System.out.println(m.matches());

        /*List<Data> tempArrearsList=new ArrayList<>();
        tempArrearsList.add(new Data("data1"));
        tempArrearsList.add(new Data("data2"));

        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("status", 1);		//添加成功标记
        resultMap.put("message", "message");		//添加返回信息
        resultMap.put("data", tempArrearsList);	//添加返回数据
        String resultStr = JSONObject.toJSONString(resultMap).toString();//HashMap转JSON
        System.out.println("JSON:"+resultStr);

        //String str="{\"data\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504513800826&di=9918185266242b89943404760d0544dd&imgtype=0&src=http%3A%2F%2Fpic4.nipic.com%2F20090806%2F2068465_130002069_2.jpg\",\"message\":\"查询广告链接成功\",\"status\":1}";
        Result result = JSONObject.parseObject(resultStr, new TypeReference<Result>() {

        });
        System.out.println(result.getStatus()+result.getMessage());
        for(int i=0;i<result.getData().size();i++)
            System.out.print(result.getData().get(i).getData().toString());
        System.out.println();

        String str="{\"datas\":[{\"data\":\"data1\"},{\"data\":\"data2\"}],\"messages\":\"message\",\"statuss\":1}";
        Result result1 = JSONObject.parseObject(str, new TypeReference<Result>() {

        });
        System.out.println(result1.getStatus()+result1.getMessage());
        System.out.println("result1==null"+(result1==null?true:false));
        System.out.println("result1.getData()==null"+(result1.getData()==null?true:false));

        String s="{\"datas\":[{\"data\":\"data1\"},{\"data\":\"data2\"}],\"message\":\"message\",\"statuss\":1}";
        Result1 r = JSONObject.parseObject(s, new TypeReference<Result1>() {

        });
        System.out.println(r.getMessage());
        System.out.println("r==null"+(r==null?true:false));
        System.out.println("r.getData()==null"+(r.getData()==null?true:false));*/
    }

    public static class Result{
        int status;
        String message;
        List<Data> data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
    }
    public static class Data{
        String data;

        public Data(String data){
            this.data=data;
        }
        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

    }

    public static class Result1{
        List<Data> data;
        String message;

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
