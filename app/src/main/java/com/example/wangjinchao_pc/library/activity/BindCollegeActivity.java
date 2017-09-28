package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.Constant.Constant;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.api.IdentifyGetSchoolListApi;
import com.example.wangjinchao_pc.library.api.IdentifyGetUserInformation;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.api.BindCollegeApi;
import com.example.wangjinchao_pc.library.enity.baseResult.BaseResultEntity;
import com.example.wangjinchao_pc.library.enity.baseResult.IdentifyBaseResultEntity;
import com.example.wangjinchao_pc.library.enity.baseResult.IdentifyBaseResultEntity2;
import com.example.wangjinchao_pc.library.enity.other.SchoolURL;
import com.example.wangjinchao_pc.library.enity.other.UNIACCOUNT;
import com.example.wangjinchao_pc.library.util.Logger;
import com.example.wangjinchao_pc.library.util.Regix;
import com.example.wangjinchao_pc.library.util.ResourceUtils;
import com.example.wangjinchao_pc.library.util.Utils;
import com.qqtheme.framework.picker.SinglePicker;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import java.util.List;

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

    private boolean bindBtnEnable=true;

    private List<SchoolURL> schoolURLs=null;

    private SchoolURL schoolURL=null;
    private String contentOfAccount="";
    private String contentOfPassword="";

    //网络请求接口
    private HttpManager httpManager;
    private BindCollegeApi bindCollegeApi;
    private IdentifyGetSchoolListApi identifyGetSchoolListApi;
    private IdentifyGetUserInformation identifyGetUserInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_college);
        ButterKnife.bind(this);
        initActionBar();
        initHttp();
        initData();
    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("绑定学校");
        setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 初始化网络相关对象
     */
    private void initHttp(){
        httpManager=new HttpManager(this,this);
        identifyGetSchoolListApi=new IdentifyGetSchoolListApi();
        bindCollegeApi=new BindCollegeApi();
        identifyGetUserInformation=new IdentifyGetUserInformation();
    }
    /**
     * 初始化数据
     */
    private void initData(){
        httpManager.doHttpDeal(identifyGetSchoolListApi);
    }

    @OnClick({R.id.college_container,R.id.college,R.id.bind})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.college_container:
            case R.id.college:
                if(schoolURLs==null||schoolURLs.size()<1)
                    break;
                onSinglePicker(schoolURLs, new SinglePicker.OnItemPickListener<SchoolURL>() {
                    @Override
                    public void onItemPicked(int index, SchoolURL item) {
                        schoolURL=item;
                        college.setText(item.getSchoolName());
                        /*editSexApi.setAllParam(MyApplication.getToken().getAccount(),Integer.toString(item.getId()));
                        httpManager.doHttpDeal(editSexApi);*/
                    }
                });
                /*Intent intent = new Intent();
                intent.setClass(this, CollegeInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, GET_CONTENT);*/

                break;
            case R.id.bind:

                contentOfAccount=account.getText().toString().trim();
                contentOfPassword=password.getText().toString().trim();
                if(schoolURL==null)
                    Utils.showToast("请选择学校");
                if(Regix.isAccount(contentOfAccount,true)==Constant.REGIX_SUCCESS
                        &&Regix.isPassword(contentOfPassword,true)==Constant.REGIX_SUCCESS){
                    identifyGetUserInformation.setAllParam(contentOfAccount,contentOfPassword,schoolURL.getUrl());
                    httpManager.doHttpDeal(identifyGetUserInformation);
                }

                /*bindCollegeApi=new BindCollegeApi(MyApplication.getToken().getAccount(),college.getText().toString(),
                        account.getText().toString(),password.getText().toString());
                httpManager.doHttpDeal(bindCollegeApi);*/
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
        if (method.equals(bindCollegeApi.getMethod())) {
            BaseResultEntity<String> result = null;
            try {
                result = JSONObject.parseObject(resulte, new TypeReference<BaseResultEntity<String>>() {

                });
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showToast("解析错误");
                Logger.e(this.getClass(), "解析错误！！！！！！！！！！");
                return;
            }
            if(result.getStatus()==Constant.SUCCESS)
                Utils.showToast("绑定成功");
            else
                Utils.showToast("绑定失败");
            /*BindPerfectActivity.start(this,);*/
        } else if (method.equals(identifyGetSchoolListApi.getMethod())) {
            IdentifyBaseResultEntity2<List<SchoolURL>> result = null;
            try {
                result = JSONObject.parseObject(resulte, new TypeReference<IdentifyBaseResultEntity2<List<SchoolURL>>>() {

                });
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showToast("解析错误");
                Logger.e(this.getClass(), "解析错误！！！！！！！！！！");
                return;
            }
            //添加数据
            if (result != null) {
                setSchoolURLs(result.getResult());
            }
        }else if (method.equals(identifyGetUserInformation.getMethod())) {
            IdentifyBaseResultEntity<UNIACCOUNT> result = null;
            try {
                result = JSONObject.parseObject(resulte, new TypeReference<IdentifyBaseResultEntity<UNIACCOUNT>>() {

                });
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showToast("解析错误");
                Logger.e(this.getClass(), "解析错误！！！！！！！！！！");
                return;
            }
            if(result.getResStatus().trim().equals(Constant.IDENTIFY_SUCCESS)){
                BindPerfectActivity.start(this,result.getObjInfo(),schoolURL);
                Utils.showToast("绑定成功");
            }
            else if(result.getResStatus().trim().equals(Constant.IDENTIFY_ERROR))
                Utils.showToast("绑定失败");
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        if(method.equals(bindCollegeApi.getMethod())){
            Utils.showToast("绑定失败");
        }else if(method.equals(identifyGetSchoolListApi.getMethod())){

        }else if(method.equals(identifyGetUserInformation.getMethod())){
            Utils.showToast("绑定失败");
        }

    }

    /**
     *选择框显示
     */
    public void onSinglePicker(List<SchoolURL> data, SinglePicker.OnItemPickListener<SchoolURL> listener) {
        SinglePicker<SchoolURL> picker = new SinglePicker<>(this, data);
        picker.setCanceledOnTouchOutside(true);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        //设置大小
        picker.setTextSize(ResourceUtils.getXmlDef(this, R.dimen.picker_content_size));
        picker.setCancelTextSize(ResourceUtils.getXmlDef(this, R.dimen.picker_cancel_size));
        picker.setSubmitTextSize(ResourceUtils.getXmlDef(this, R.dimen.picker_submit_size));
        picker.setOnItemPickListener(listener);
        picker.show();
    }

    public List<SchoolURL> getSchoolURLs() {
        return schoolURLs;
    }

    public void setSchoolURLs(List<SchoolURL> schoolURLs) {
        this.schoolURLs = schoolURLs;
    }
}
