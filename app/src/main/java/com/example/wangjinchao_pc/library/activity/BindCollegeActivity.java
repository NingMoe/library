package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.enity.api.BindCollegeApi;
import com.example.wangjinchao_pc.library.enity.api.GetCollegeApi;
import com.example.wangjinchao_pc.library.enity.result.BaseResultEntity;
import com.example.wangjinchao_pc.library.util.Utils;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjinchao-PC on 2017/7/8.
 */

public class BindCollegeActivity extends ToolbarActivity implements View.OnClickListener,HttpOnNextListener {

    private static final int GET_CONTENT=10;

    public static void start(Context context){
        Intent intent =new Intent(context,BindCollegeActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.college_container)
    LinearLayout college_container;
    @BindView(R.id.college)
    EditText college;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.bind)
    Button bind;

    //网络请求接口
    private HttpManager httpManager;
    private BindCollegeApi bindCollegeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colloge_bind);
        ButterKnife.bind(this);
        initActionBar();
        httpManager=new HttpManager(this,this);
    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("绑定学校");
        setDisplayHomeAsUpEnabled(true);
    }

    @OnClick({R.id.college_container,R.id.college,R.id.bind})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.college_container:
            case R.id.college:
                Intent intent = new Intent();
                intent.setClass(this, CollegeInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, GET_CONTENT);
                break;
            case R.id.bind:
                bindCollegeApi=new BindCollegeApi(MyApplication.getToken(),college.getText().toString(),
                        account.getText().toString(),password.getText().toString());
                httpManager.doHttpDeal(bindCollegeApi);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GET_CONTENT:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    college.setText(bundle.getString(CollegeInfoActivity.COLLEGE));
                }
                break;
        }
    }

    @Override
    public void onNext(String resulte, String method) {
        if(method.equals(bindCollegeApi.getMethod())){
            BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<String>>() {
                    });

            Utils.showToast("绑定成功");
            BindPerfectActivity.start(this);
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        if(method.equals(bindCollegeApi.getMethod())){
            Utils.showToast("绑定失败");
        }

        //————————————————
        BindPerfectActivity.start(this);
    }
}
