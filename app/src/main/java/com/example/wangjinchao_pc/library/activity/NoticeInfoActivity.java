package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.adapter.NoticeAdapter;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;


import java.util.HashMap;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/12.
 */

public class NoticeInfoActivity extends ToolbarActivity {
    public static void start(Context context,HashMap<String,Object> data){
        Intent intent =new Intent(context,NoticeInfoActivity.class);

        intent.putExtra(NoticeAdapter.CONTENT,data.get(NoticeAdapter.CONTENT).toString());
        intent.putExtra(NoticeAdapter.TIME,data.get(NoticeAdapter.TIME).toString());

        context.startActivity(intent);
    }

    String cont;
    String time;

    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.ok)
    Button ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_notice_info);
        ButterKnife.bind(this);
        initActionBar();

        cont=getIntent().getStringExtra(NoticeAdapter.CONTENT);
        time=getIntent().getStringExtra(NoticeAdapter.TIME);

        content.setText(cont+"\n\n\n\n\n\n\n\n\n\n\n\n\n\n"+time);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("通知详情");
        setDisplayHomeAsUpEnabled(false);
    }


}
