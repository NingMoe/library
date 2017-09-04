package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.enity.api.GetCodeApi;
import com.example.wangjinchao_pc.library.enity.api.ResetPasswordApi;
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

public class SetPasswdActivity extends ToolbarActivity implements View.OnClickListener,HttpOnNextListener {
    private final static String NUMBER="number";
    private final static String CODE="code";
    public static void start(Context context,String number,String code){
        Intent intent =new Intent(context,SetPasswdActivity.class);
        intent.putExtra(NUMBER,number);
        intent.putExtra(CODE,code);

        context.startActivity(intent);
    }

    @BindView(R.id.password)
    EditText passwd;
    @BindView(R.id.password2)
    EditText passwd2;
    @BindView(R.id.ok)
    Button ok;

    private boolean btn_flag;

    String number;
    String code;
    String nowPassword;
    String newPassword;

    //网络请求接口
    private HttpManager httpManager;
    private ResetPasswordApi resetPasswordApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_passwd);
        ButterKnife.bind(this);
        httpManager=new HttpManager(this,this);

        number=getIntent().getStringExtra(NUMBER);
        code=getIntent().getStringExtra(CODE);

        initActionBar();
    }
    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle(R.string.set_passwd);
        setDisplayHomeAsUpEnabled(true);
    }

    @OnClick({R.id.ok})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ok:
                nowPassword=passwd.getText().toString();
                newPassword=passwd2.getText().toString();
                if(nowPassword.equals(newPassword)){
                    resetPasswordApi=new ResetPasswordApi(number,code,newPassword);
                    httpManager.doHttpDeal(resetPasswordApi);
                }else{
                    Utils.showToast("密码不一致");
                }
                break;
        }
    }

    @Override
    public void onNext(String resulte, String method) {
        if (method.equals(resetPasswordApi.getMethod())) {
            BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<String>>() {
                    });
            Utils.showToast("发送成功");
        }

        MainActivity.start(this);
    }

    @Override
    public void onError(ApiException e, String method) {
        Utils.showToast(e.getDisplayMessage());
        //测试——————————————————————
        MainActivity.start(this);
    }
}
