package com.example.wangjinchao_pc.library.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.wangjinchao_pc.library.Constant.Constant;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.BaseActivity;
import com.example.wangjinchao_pc.library.enity.api.AdvertisementApi;
import com.example.wangjinchao_pc.library.enity.result.Advertisement;
import com.example.wangjinchao_pc.library.enity.result.BaseResultEntity;
import com.example.wangjinchao_pc.library.util.Logger;
import com.example.wangjinchao_pc.library.util.Utils;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AdActivity extends BaseActivity implements View.OnClickListener,HttpOnNextListener {

    private final static int AD_UNLOAD=0;
    private final static int AD_LOADING=1;
    private final static int AD_LOAD=2;
    private final static int AD_CANCEL=3;

    @BindView(R.id.layout_skip)
    LinearLayout layout_skip;
    @BindView(R.id.background)
    ImageView background;
    @BindView(R.id.skip)
    TextView skip;

    //网络请求接口
    private AdvertisementApi advertisementApi;
    private HttpManager httpManager;
    //计数标志
    private boolean continueCount=true;  //控制延迟是否继续
    private int begin_flag =AD_UNLOAD;
    private int timeCount=0;
    //控制广告时长
    private int initTimeCount=3;

    //互斥
    private boolean flag=true;

    //延迟处理
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            countNum();
            if (continueCount)
                handler.sendMessageDelayed(handler.obtainMessage(-1),1000);
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        ButterKnife.bind(this);

        init();

        handler.sendMessageDelayed(handler.obtainMessage(-1),1000);

        httpManager.doHttpDeal(advertisementApi);
        /*test();*/
    }
    /**
     * 初始化
     */
    void init(){
        timeCount=0;
        initTimeCount=3;
        continueCount=true;
        begin_flag =AD_UNLOAD;
        advertisementApi=new AdvertisementApi();
        httpManager=new HttpManager(this,this);
    }

    /**
     * 计数
     */
    private int countNum() {
        timeCount++;
        Log.d("timeCount",timeCount+"");
        if(begin_flag ==AD_LOADING){
            timeCount=0;
            layout_skip.setVisibility(View.VISIBLE);
            begin_flag =AD_LOAD;
        }
        if(begin_flag ==AD_LOAD){
            skip.setText("跳过"+(3-timeCount));
        }
        //当时间达到initTimeCount时，自动跳过
        if (timeCount >= initTimeCount|| begin_flag ==AD_CANCEL) {
            continueCount = false;
            toNextActivity();
            finish();
        }
        return timeCount;
    }

    /**
     * 页面跳转
     */
    public void toNextActivity() {//根据是否保存有 token 判断去登录界面还是主界面
        if(flag)
            flag=false;
        else
            return;
        Logger.d(this.getClass(),"到下一个界面");
        Intent intent = null;
        String token=MyApplication.getToken();     //?????
        if (TextUtils.isEmpty(token)) {
            intent = new Intent(AdActivity.this, LoginActivity.class);
        } else {
            intent = new Intent(AdActivity.this, MainActivity.class);
            MyApplication.setToken(token);
        }
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.background, R.id.layout_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.background:
                if(begin_flag==AD_LOAD){

                }
                break;
            case R.id.layout_skip:
                continueCount = false;
                toNextActivity();
                finish();
                break;
        }
    }


    //测试
    private void test(){
        Glide.with(this.getApplicationContext())
                .load(Uri.parse("https://b-ssl.duitang.com/uploads/item/201404/28/20140428171324_5v5fL.jpeg"))
                .crossFade(300)
                .placeholder(R.drawable.background)
                .listener(new RequestListener<Uri, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                        begin_flag =AD_CANCEL;
                        Utils.showToast(e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        begin_flag =AD_LOADING;
                        return false;
                    }
                })
                .into(background);
    }


    @Override
    public void onNext(String result, String method) {

        Logger.d(this.getClass(), "onNext"+result);
        BaseResultEntity<String> advertisement=null;
        try{
            advertisement = JSONObject.parseObject(result, new
                    TypeReference<BaseResultEntity<String>>() {
                    });
        }catch (Exception e){
            Logger.d(this.getClass(),"解析json错误："+result);
            begin_flag =AD_CANCEL;
            return;
        }
        if(advertisement==null||advertisement.getStatus()== Constant.ERROR){
            begin_flag =AD_CANCEL;
            return;
        }

        //加载图片
        Glide.with(this.getApplicationContext())
                .load(Uri.parse(advertisement.getData().toString()))
                .crossFade(300)
                .placeholder(R.drawable.background)
                .listener(new RequestListener<Uri, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                        begin_flag =AD_CANCEL;
                        Utils.showToast(e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        begin_flag =AD_LOADING;
                        return false;
                    }
                })
                .into(background);
    }

    @Override
    public void onError(ApiException e, String method) {
        begin_flag =AD_CANCEL;
        Utils.showToast(e.getDisplayMessage());
    }
}
