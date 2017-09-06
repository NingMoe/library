package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.Constant.Configure;
import com.example.wangjinchao_pc.library.Constant.Constant;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.enity.api.GetPasswordCodeApi;
import com.example.wangjinchao_pc.library.enity.api.GetRegisterCodeApi;
import com.example.wangjinchao_pc.library.enity.api.RegisterApi;
import com.example.wangjinchao_pc.library.enity.result.BaseResultEntity;
import com.example.wangjinchao_pc.library.util.Logger;
import com.example.wangjinchao_pc.library.util.Utils;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjinchao-PC on 2017/7/5.
 */

public class RegisterActivity extends ToolbarActivity implements View.OnClickListener,HttpOnNextListener{
    public static void start(Context context){
        Intent intent =new Intent(context,RegisterActivity.class);
        context.startActivity(intent);
    }
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.password)
    EditText passwd;
    @BindView(R.id.nickname)
    EditText nickname;
    @BindView(R.id.certify)
    EditText certify;
    @BindView(R.id.get_code)
    Button get_code;
    @BindView(R.id.is_agree)
    RadioButton is_agree;
    @BindView(R.id.register)
    Button register;

    //验证码是否可获取(同时判断按钮是否可以按下)
    private boolean getCodeEnable=true;
    //验证码时效
    private int count= Configure.Code_Time;

    //网络请求接口
    private HttpManager httpManager;
    private GetRegisterCodeApi getCodeApi;
    private RegisterApi registerApi;

    //计时
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            count--;
            get_code.setText("获取验证码"+count);
            if(count>0&&!getCodeEnable)
                handler.sendMessageDelayed(handler.obtainMessage(-1),1000);
            else{
                get_code.setText("获取验证码");
                getCodeEnable=true;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initActionBar();
        initHttp();
    }
    /**
     * 初始化状态栏
     */
    private void initActionBar(){
        setTitle(R.string.register);
        setDisplayHomeAsUpEnabled(true);
    }
    /**
     * 初始化网络相关对象
     */
    private void initHttp(){
        httpManager=new HttpManager(this,this);
        getCodeApi=new GetRegisterCodeApi();
        registerApi=new RegisterApi();
    }


    @OnClick({R.id.get_code,R.id.register})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.get_code:
                //正则表达式?????????????????????????????
                if(true&&getCodeEnable){
                    getCodeEnable=false;
                    count=Configure.Code_Time;
                    getCodeApi.setNumber(mobile.getText().toString());
                    httpManager.doHttpDeal(getCodeApi);
                }
                break;
            case R.id.register:
                //正则表达式?????????????????????????????if(true&&!flag)
                if(true){
                    registerApi.setAllParam(mobile.getText().toString(),passwd.getText().toString(),nickname.getText().toString(),certify.getText().toString());
                    httpManager.doHttpDeal(registerApi);
                }
                break;

        }
    }

    @Override
    public void onNext(String resulte, String method) {
        boolean flag=true;
        if (method.equals(getCodeApi.getMethod())) {
            BaseResultEntity<String> result=null;
            try{
                result = JSONObject.parseObject(resulte, new TypeReference<BaseResultEntity<String>>() {

                });
            }catch (Exception e){
                e.printStackTrace();
                getCodeEnable=true;
                flag=false;
                Utils.showToast("解析错误");
                Logger.e(this.getClass(),"解析错误！！！！！！！！！！");
                return;
            }
            //添加数据
            if(result!=null) {
                if(result.getStatus()== Constant.SUCCESS)
                        flag=true;
                else if(result.getStatus()== Constant.ERROR)
                    flag=false;
            }
            if(true&&flag){
                handler.sendMessageDelayed(handler.obtainMessage(-1),1000);
            }
            if(flag)
                Utils.showToast("发送成功");
            else{
                getCodeEnable=true;
                Utils.showToast("发送失败");
            }

        }else if(method.equals(registerApi.getMethod())){
            LoginActivity.start(this);
            Utils.showToast("注册成功");
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        Utils.showToast(e.getDisplayMessage());
        if (method.equals(getCodeApi.getMethod())) {
            getCodeEnable=true;
        }else if(method.equals(registerApi.getMethod())){
            Utils.showToast("注册失败");
            //测试————————————————————————————
            LoginActivity.start(this);
        }
    }
}
