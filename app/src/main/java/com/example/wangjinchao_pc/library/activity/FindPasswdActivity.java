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

    private String numberOfPhone="";
    private String code="";

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
                get_code.setClickable(true);
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
                numberOfPhone=mobile_number.getText().toString().trim();
                if(Regix.isMobile(numberOfPhone,true)&& getCodeEnable){
                    getCodeEnable =false;
                    get_code.setClickable(false);
                    count=Configure.Code_Time;
                    getPasswordCodeApi.setAllParam(numberOfPhone);
                    httpManager.doHttpDeal(getPasswordCodeApi);
                }else
                    Utils.showToast("手机号错误");
                break;
            case R.id.next:
                numberOfPhone=mobile_number.getText().toString().trim();
                code=get_code.getText().toString().trim();
                if(numberOfPhone.equals(""))
                    Utils.showToast("手机号不能为空");
                else if(code.equals(""))
                    Utils.showToast("验证码不能为空");
                //正则表达式?????????????????????????????
                if(Regix.isMobile(numberOfPhone,true)&&true){
                    SetPasswdActivity.start(this,numberOfPhone,code);
                }else
                    Utils.showToast("验证码错误");
                break;
        }
    }

    @Override
    public void onNext(String resulte, String method) {
        boolean flag=true;
        if (method.equals(getPasswordCodeApi.getMethod())) {
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
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        Utils.showToast(e.getDisplayMessage());
        if (method.equals(getPasswordCodeApi.getMethod())) {
            getCodeEnable=true;
        }else if(method.equals(getPasswordCodeApi.getMethod())){
            Utils.showToast("注册失败");
            //测试————————————————————————————
            LoginActivity.start(this);
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
