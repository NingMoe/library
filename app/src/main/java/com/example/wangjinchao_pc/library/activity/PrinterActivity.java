package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/9/11.
 */

public class PrinterActivity extends ToolbarActivity {
    public static final String URL = "url";

    @BindView(R.id.webview)WebView webView;

    public static void start(Context context, String url, String bookname){
        Intent intent = new Intent(context, PrinterActivity.class);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        ButterKnife.bind(this);
        initActionBar();
        webView.loadUrl(getIntent().getStringExtra(URL));
    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("云打印");
        setDisplayHomeAsUpEnabled(true);
    }
}
