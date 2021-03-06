package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.Constant.Constant;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.enity.Token;
import com.example.wangjinchao_pc.library.api.GetUserInformationApi;
import com.example.wangjinchao_pc.library.api.LoginApi;
import com.example.wangjinchao_pc.library.enity.domain.User;
import com.example.wangjinchao_pc.library.enity.baseResult.BaseResultEntity;
import com.example.wangjinchao_pc.library.enity.baseResult.BaseResultEntity2;
import com.example.wangjinchao_pc.library.util.Logger;
import com.example.wangjinchao_pc.library.util.Regix;
import com.example.wangjinchao_pc.library.util.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjinchao-PC on 2017/7/5.
 */

public class LoginActivity extends ToolbarActivity implements View.OnClickListener,HttpOnNextListener{

    public static void start(Context context){
        Intent intent =new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.headPhoto)
    SimpleDraweeView headPhoto;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText passwd;
    @BindView(R.id.select_passwd)
    TextView select_passwd;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;

    //网络请求接口
    private LoginApi loginApi;
    private GetUserInformationApi getUserInformationApi;
    private HttpManager httpManager;

    private String contentOfAccount="";
    private String contentOfPassword="";

    private boolean loginBtnEnable=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initActionBar();
        initHttp();
        initData();

    }
    /**
     * 初始化状态栏
     */
    private void initActionBar(){
        setTitle(R.string.login);
        setDisplayHomeAsUpEnabled(false);
    }
    /**
     * 初始化状态栏
     */
    private void initHttp(){
        httpManager=new HttpManager(this,this);
        loginApi=new LoginApi();
        getUserInformationApi=new GetUserInformationApi();
    }

    private void initData(){
        account.setText(MyApplication.getToken().getAccount());
        passwd.setText(MyApplication.getToken().getPassword());
    }

    @OnClick({R.id.select_passwd, R.id.login,R.id.register})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.select_passwd:
                FindPasswdActivity.start(this);
                break;
            case R.id.login:
                if(!loginBtnEnable)
                    return;
                loginBtnEnable=false;

                contentOfAccount=account.getText().toString().trim();
                contentOfPassword=passwd.getText().toString().trim();
                Log.d("wjc",contentOfAccount+"  "+contentOfPassword);
                //判定
                if(Regix.isAccount(contentOfAccount,true)==Constant.REGIX_SUCCESS
                        &&Regix.isPassword(contentOfPassword,true)==Constant.REGIX_SUCCESS){
                    loginApi.setAllParam(contentOfAccount,contentOfPassword);
                    httpManager.doHttpDeal(loginApi);
                }else
                    loginBtnEnable=true;
                break;
            case R.id.register:

                RegisterActivity.start(this);
                break;
            default:
                break;

        }
    }

    @Override
    public void onNext(String resulte, String method) {
        if (method.equals(loginApi.getMethod())) {
            BaseResultEntity2<String> result=null;
            try{
                result = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity2<String>>() {
                        });
            }catch (Exception e){
                loginBtnEnable=true;
                e.printStackTrace();
                Utils.showToast("解析错误");
                Logger.e(this.getClass(),"解析错误！！！！！！！！！！");
                return;
            }
            if(result!=null) {
                if(result.getResult()== Constant.SUCCESS){
                    //成功后再获取个人信息
                    getUserInformationApi.setAllParam(contentOfAccount);
                    httpManager.doHttpDeal(getUserInformationApi);
                }
                else {
                    Utils.showErrorMsgToast(result.getErr_msg(),"登陆失败");
                    loginBtnEnable=true;
                }
            }
        }else if(method.equals(getUserInformationApi.getMethod())) {
            BaseResultEntity<User> result=null;
            try{
                result = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<User>>() {
                        });
            }catch (Exception e){
                loginBtnEnable=true;

                e.printStackTrace();
                Utils.showToast("解析错误");
                Logger.e(this.getClass(),"解析错误！！！！！！！！！！");
                return;
            }
            if(result!=null) {
                if(result.getStatus()== Constant.SUCCESS){
                    MyApplication.setUser(result.getData());
                    MyApplication.setToken(new Token(contentOfAccount,contentOfPassword));
                    Utils.showToast("登陆成功");
                    MainActivity.start(this);
                }
                else {
                    Utils.showErrorMsgToast(result.getMessage(),"登陆失败");
                }
            }
            loginBtnEnable=true;
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        if (method.equals(loginApi.getMethod())) {
            Utils.showToast(e.getDisplayMessage());
            loginBtnEnable=true;
        }else if(method.equals(getUserInformationApi.getMethod())) {
            Utils.showToast(e.getDisplayMessage());
            loginBtnEnable=true;
        }
    }
}
