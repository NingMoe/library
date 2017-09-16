package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.util.Util;
import com.example.wangjinchao_pc.library.Constant.Configure;
import com.example.wangjinchao_pc.library.Constant.Constant;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.enity.api.GetPasswordCodeApi;
import com.example.wangjinchao_pc.library.enity.result.BaseResultEntity;
import com.example.wangjinchao_pc.library.enity.result.BaseResultEntity2;
import com.example.wangjinchao_pc.library.util.Logger;
import com.example.wangjinchao_pc.library.util.Regix;
import com.example.wangjinchao_pc.library.util.Utils;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjinchao-PC on 2017/7/8.
 */

public class FindPasswdActivity extends ToolbarActivity implements View.OnClickListener,HttpOnNextListener{
    public static int FINDPASSWORD=1;
    public static void start(Context context){
        Intent intent =new Intent(context,FindPasswdActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.mobile)
    EditText mobile_number;
    @BindView(R.id.certify)
    EditText certify;
    @BindView(R.id.get_code)
    Button get_code;
    @BindView(R.id.next)
    Button next;

    //验证码是否可获取(同时判断按钮是否可以按下)
    private boolean getCodeEnable =true;
    //验证码时效
    private int count= Configure.Code_Time;

    //网络请求接口
    private HttpManager httpManager;
    private GetPasswordCodeApi getPasswordCodeApi;

    private String contentOfPhoto="";
    private String contentOfCode="";

    private String MyCode="";

    //计时
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            count--;
            get_code.setText("验证码 "+count);
            if(count>0&&!getCodeEnable)
                handler.sendMessageDelayed(handler.obtainMessage(-1),1000);
            else{
                getCodeEnable=true;
                MyCode="";
                get_code.setText("获取验证码");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_passwd);
        ButterKnife.bind(this);

        initActionBar();
        initHttp();
    }
    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle(R.string.select_passwd);
        setDisplayHomeAsUpEnabled(true);
    }
    /**
     * 初始化网络相关对象
     */
    private void initHttp(){
        httpManager=new HttpManager(this,this);
        getPasswordCodeApi=new GetPasswordCodeApi();
    }

    @OnClick({R.id.get_code, R.id.next})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.get_code:
                if(!getCodeEnable)
                    break;
                MyCode="";

                contentOfPhoto=mobile_number.getText().toString().trim();
                if(Regix.isMobile(contentOfPhoto,true)==Constant.REGIX_SUCCESS&& getCodeEnable){
                    getCodeEnable =false;
                    count=Configure.Code_Time;
                    getPasswordCodeApi.setAllParam(contentOfPhoto);
                    httpManager.doHttpDeal(getPasswordCodeApi);
                }
                break;
            case R.id.next:
                contentOfPhoto=mobile_number.getText().toString().trim();
                contentOfCode=certify.getText().toString().trim();
                if(Regix.isMobile(contentOfPhoto,true)==Constant.REGIX_SUCCESS&&Regix.isCode(contentOfCode,true)==Constant.REGIX_SUCCESS){
                    Logger.d(this.getClass(),MyCode+"   "+contentOfCode+MyCode.equals(contentOfCode));
                    if(MyCode.equals(contentOfCode))
                        SetPasswdActivity.start(this,contentOfPhoto,contentOfCode);
                    else
                        Utils.showToast("验证码错误");
                }
                break;
        }
    }

    @Override
    public void onNext(String resulte, String method) {
        if (method.equals(getPasswordCodeApi.getMethod())) {
            BaseResultEntity2<String> result=null;
            try{
                result = JSONObject.parseObject(resulte, new TypeReference<BaseResultEntity2<String>>() {

                });
            }catch (Exception e){
                e.printStackTrace();
                getCodeEnable=true;
                Utils.showToast("解析错误");
                Logger.e(this.getClass(),"解析错误！！！！！！！！！！");
                return;
            }

            //添加数据
            if(result!=null) {
                if(result.getResult()== Constant.SUCCESS){
                    Utils.showToast("发送成功");
                    MyCode=result.getCode();
                    handler.sendMessageDelayed(handler.obtainMessage(-1),1000);
                }
                else if(result.getResult()== Constant.ERROR){
                    Utils.showToast("发送失败");
                    getCodeEnable=true;
                }
            }else
                getCodeEnable=true;
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        Utils.showToast(e.getDisplayMessage());
        if (method.equals(getPasswordCodeApi.getMethod())) {
            Utils.showToast("发送失败");
            getCodeEnable=true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==FINDPASSWORD&&resultCode==RESULT_OK){
            Logger.d(this.getClass(),"关闭Activity");
            this.finish();
        }
    }
}
