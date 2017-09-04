package com.example.wangjinchao_pc.library.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.enity.api.SubjectPostApi;
import com.example.wangjinchao_pc.library.enity.result.BaseResultEntity;
import com.example.wangjinchao_pc.library.enity.result.SubjectResulte;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;

/**
 * Created by wangjinchao-PC on 2017/7/29.
 */

public class TestActivity extends RxAppCompatActivity implements View.OnClickListener, HttpOnNextListener {

    //    公用一个HttpManager
    private HttpManager manager;
    //    post请求接口信息
    private SubjectPostApi postEntity;

    private Button btn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ImageView img = (ImageView) findViewById(R.id.img);
        Drawable drawable = img.getDrawable();
        DrawableCompat.setTint(drawable, Color.RED);
        img.setImageDrawable(drawable);
        img.setClickable(true);

        btn=(Button)findViewById(R.id.btn);
        textView=(TextView)findViewById(R.id.text);
        btn.setOnClickListener(this);

        /*初始化数据*/
        manager = new HttpManager(this, this);

        postEntity = new SubjectPostApi();
        postEntity.setAll(true);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn){
            manager.doHttpDeal(postEntity);
        }
    }

    @Override
    public void onNext(String resulte, String method) {
        if (method.equals(postEntity.getMethod())) {
            BaseResultEntity<ArrayList<SubjectResulte>> subjectResulte = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<ArrayList<SubjectResulte>>>() {
                    });
            textView.setText("post返回：\n" + subjectResulte.getData().toString());
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        textView.setText("失败："+method+"\ncode=" + e.getCode() + "\nmsg:" + e.getDisplayMessage());
    }
}
