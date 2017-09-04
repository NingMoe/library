package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;

import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/8.
 */

public class InformationModifyActivity extends ToolbarActivity implements View.OnClickListener {



    public static void start(Context context){
        Intent intent =new Intent(context,InformationModifyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getSimpleName(),"onCreate");
        setContentView(R.layout.activity_information_part);
        ButterKnife.bind(this);

        //监听点击

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.nickname_container:


                break;
            case R.id.sex_container:


                break;
            case R.id.interest_container:


                break;
            case R.id.academy_container:


                break;
            case R.id.profession_container:


                break;

        }
    }
}
