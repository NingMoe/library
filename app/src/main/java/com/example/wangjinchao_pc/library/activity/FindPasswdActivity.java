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
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.enity.api.GetCodeApi;
import com.example.wangjinchao_pc.library.enity.api.LoginApi;
import com.example.wangjinchao_pc.library.enity.result.BaseResultEntity;
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
    private boolean flag=true;
    //验证码时效
    private int count=60;

    //网络请求接口
    private HttpManager httpManager;
    private GetCodeApi getCodeApi;

    //计时
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            count--;
            get_code.setText("获取验证码"+count);
            if(count>0&&!flag)
                handler.sendMessageDelayed(handler.obtainMessage(-1),1000);
            else
                get_code.setText("获取验证码");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_passwd);
        ButterKnife.bind(this);
        httpManager=new HttpManager(this,this);

        initActionBar();
    }
    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle(R.string.select_passwd);
        setDisplayHomeAsUpEnabled(true);
    }

    @OnClick({R.id.get_code, R.id.next})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.get_code:
                //正则表达式?????????????????????????????
                if(true&&flag==true){
                    flag=false;
                    count=60;
                    getCodeApi=new GetCodeApi(mobile_number.getText().toString());
                    httpManager.doHttpDeal(getCodeApi);
                }
                break;
            case R.id.next:
                //正则表达式?????????????????????????????
                if(true&&true){
                    SetPasswdActivity.start(this,mobile_number.getText().toString(),get_code.getText().toString());
                }
                break;
        }
    }

    @Override
    public void onNext(String resulte, String method) {
        if (method.equals(getCodeApi.getMethod())) {
            BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<String>>() {
                    });
            if(true){
                handler.sendMessageDelayed(handler.obtainMessage(-1),1000);
            }
            Utils.showToast("发送成功");
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        Utils.showToast(e.getDisplayMessage());
        flag=true;
    }
}
