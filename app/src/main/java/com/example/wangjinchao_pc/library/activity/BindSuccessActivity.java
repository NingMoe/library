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
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.api.BindCollegeApi;
import com.example.wangjinchao_pc.library.enity.baseResult.BaseResultEntity;
import com.example.wangjinchao_pc.library.enity.mine.BindParam;
import com.example.wangjinchao_pc.library.util.Utils;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjinchao-PC on 2017/7/13.
 */

public class BindSuccessActivity extends ToolbarActivity implements View.OnClickListener {
    public static final String BINDPARAM="bindparam";
    public static void start(Context context, BindParam bindParam){
        Intent intent =new Intent(context,BindSuccessActivity.class);
        intent.putExtra(BINDPARAM,bindParam);
        context.startActivity(intent);
    }

    @BindView(R.id.college)
    EditText college;
    @BindView(R.id.number)
    EditText number;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.academy)
    EditText academy;
    @BindView(R.id.profession)
    EditText prodession;
    @BindView(R.id.sex)
    EditText sex;
    @BindView(R.id.identify)
    EditText identify;
    @BindView(R.id.time)
    EditText time;

    @BindView(R.id.next)
    Button next;

    private BindParam bindParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_success);
        ButterKnife.bind(this);
        bindParam=(BindParam) getIntent().getSerializableExtra(BINDPARAM);
        initActionBar();
        init();
        next.setOnClickListener(this);
    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("信息核对");
        setDisplayHomeAsUpEnabled(true);
    }

    private void init(){
        if(bindParam!=null){
            college.setText(bindParam.getSchoolName());
            number.setText(bindParam.getStudentid());
            name.setText(bindParam.getTrueName());
            academy.setText(bindParam.getCollegeName());
            prodession.setText(bindParam.getMajorName());
            sex.setText(bindParam.getSex());
            identify.setText(bindParam.getIdent());
            time.setText(bindParam.getEnrolYear());
        }
        college.setEnabled(false);
        number.setEnabled(false);
        name.setEnabled(false);
        academy.setEnabled(false);
        prodession.setEnabled(false);
        sex.setEnabled(false);
        identify.setEnabled(false);
        time.setEnabled(false);

        next.setText("确认");
    }


    @OnClick({R.id.next})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.next:
                MainActivity.start(this);
        }
    }
}
