package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjinchao-PC on 2017/7/8.
 */

public class LeaveWorkActivity extends ToolbarActivity implements View.OnClickListener {

    public static void start(Context context){
        Intent intent =new Intent(context,LeaveWorkActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.debt_search)
    LinearLayout debt_search;
    @BindView(R.id.book_un_return)
    LinearLayout book_un_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        ButterKnife.bind(this);
        initActionBar();
    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("离馆业务");
        setDisplayHomeAsUpEnabled(true);
    }

    @OnClick({R.id.debt_search, R.id.book_un_return})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.debt_search:
                DebtDetailActivity.start(this,DebtDetailActivity.TYPE_PRICE);
                break;
            case R.id.book_un_return:
                DebtDetailActivity.start(this,DebtDetailActivity.TYPE_BOOK);
                break;

        }
    }
}
