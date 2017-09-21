package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.util.FileHelper;

import android.net.Uri;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wangjinchao_pc.library.Constant.Configure.PHOTOURL_PREX;

/**
 * Created by wangjinchao-PC on 2017/7/11.
 */

public class ShowPhotoActivity extends ToolbarActivity {
    public static void start(Context context){
        Intent intent =new Intent(context,ShowPhotoActivity.class);
        context.startActivity(intent);
    }


    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        ButterKnife.bind(this);

        initActionBar();
        Glide.with(this.getApplicationContext())
                .load(PHOTOURL_PREX+ MyApplication.getToken().getAccount()+".jpg")
                .crossFade(0)
                .placeholder(R.mipmap.headphoto)
                .into(img);
    }
    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("头像");
    }
}
