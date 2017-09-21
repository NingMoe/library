package com.example.wangjinchao_pc.library.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.wangjinchao_pc.library.BuildConfig;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.BaseActivity;
import com.example.wangjinchao_pc.library.enity.Token;
import com.example.wangjinchao_pc.library.enity.domain.User;
import com.example.wangjinchao_pc.library.util.FileHelper;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.retrofit_rx.RxRetrofitApp;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by hongzhiyuanzj on 2017/4/22.
 */
public class MyApplication extends Application {

    public static List<?> images=new ArrayList<>();
    public static List<String> titles=new ArrayList<>();

    private static MyApplication instance;
    private static Context context;

    //维持登陆状态???????需要保存吗？？？？
    private static Token token;
    private static User user;

    private Stack<BaseActivity> activityStack;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyApplication","start");
        context = getApplicationContext();
        instance = this;
        Fresco.initialize(this);
        createActivityStack();

        RxRetrofitApp.init(this, BuildConfig.DEBUG);
        init();

        String[] urls = getResources().getStringArray(R.array.url);
        String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        images = new ArrayList(list);

        List list1 = Arrays.asList(tips);
        titles= new ArrayList(list1);
        Log.d("MyApplication","end");



        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    private void init(){
        token=FileHelper.getInstance().getToken(getContext());
    }
    public static Token getToken() {
        if(token==null)
            token=new Token("","");
        return token;
    }

    public static void setToken(Token token) {
        MyApplication.token = token;
        FileHelper.getInstance().saveToken(getContext(),token);
    }

    public static User getUser() {
        if(user==null)
            user=new User();
        return user;
    }

    public static void setUser(User user) {
        MyApplication.user = user;
    }

    private void createActivityStack(){

        activityStack = new Stack<>();
    }

    public static MyApplication getInstance(){
        return instance;
    }

    public static Context getContext(){
        return context;
    }


    public void pushActivity(BaseActivity baseActivity){

        activityStack.push(baseActivity);
    }

    public void popActivity(){
        activityStack.pop();
    }

    /*public void changeState(){
        for(BaseActivity activity: activityStack){
            if(activity instanceof BookDetailActivity){
                ((BookDetailActivity) activity).getTalk();
            }
            if(activity instanceof MainActivity){
                ((MainActivity) activity).initUI();
            }
        }

    }*/
}
