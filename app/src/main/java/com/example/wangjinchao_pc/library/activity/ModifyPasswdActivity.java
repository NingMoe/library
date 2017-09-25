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
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.api.SetPasswordApi;
import com.example.wangjinchao_pc.library.enity.baseResult.BaseResultEntity;
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

public class ModifyPasswdActivity extends ToolbarActivity implements View.OnClickListener,HttpOnNextListener{

    public static void start(Context context){
        Intent intent =new Intent(context,ModifyPasswdActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.now_password)
    EditText now_password;
    @BindView(R.id.new_password)
    EditText new_password;
    @BindView(R.id.new_password2)
    EditText getNew_password2;
    @BindView(R.id.ok)
    Button OK;

    //网络请求接口
    private HttpManager httpManager;
    private SetPasswordApi setPasswordApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwd_reset);
        ButterKnife.bind(this);
        httpManager=new HttpManager(this,this);

        initActionBar();
    }
    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("修改密码");
/*        setDisplayHomeAsUpEnabled(true);*/
    }

    @OnClick({R.id.ok})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ok:
                if(true){
                    setPasswordApi=new SetPasswordApi(MyApplication.getToken().getAccount(),now_password.getText().toString(),new_password.getText().toString());
                    httpManager.doHttpDeal(setPasswordApi);
                }
                break;
        }
    }

    @Override
    public void onNext(String resulte, String method) {
        if(method.equals(setPasswordApi.getMethod())){
            BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<String>>() {
                    });

            Utils.showToast("修改成功");
            MainActivity.start(this);
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        if(method.equals(setPasswordApi.getMethod())){
            Utils.showToast("修改失败");
        }
        MainActivity.start(this);
    }
}
