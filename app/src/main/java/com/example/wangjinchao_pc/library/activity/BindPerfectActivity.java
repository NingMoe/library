package com.example.wangjinchao_pc.library.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.enity.other.SchoolURL;
import com.example.wangjinchao_pc.library.enity.other.UNIACCOUNT;
import com.example.wangjinchao_pc.library.enity.picker.Category;
import com.example.wangjinchao_pc.library.util.ResourceUtils;
import com.qqtheme.framework.picker.SinglePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjinchao-PC on 2017/7/13.
 */

public class BindPerfectActivity extends ToolbarActivity implements View.OnClickListener,View.OnFocusChangeListener{

    public static final String ACCOUNTINFO="accountinfo";
    public static final String SCHOOLURL="schoolurl";
    public static void start(Context context, UNIACCOUNT uniaccount, SchoolURL schoolURL){
        Intent intent =new Intent(context,BindPerfectActivity.class);
        intent.putExtra(ACCOUNTINFO,uniaccount);
        intent.putExtra(SCHOOLURL,schoolURL);
        context.startActivity(intent);
    }

    private static final int DIALOG_DATE=100;

    @BindView(R.id.college)
    EditText college;
    @BindView(R.id.number)
    EditText number;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.academy)
    EditText academy;
    @BindView(R.id.profession)
    EditText profession;
    @BindView(R.id.sex)
    EditText sex;
    @BindView(R.id.identify)
    EditText identify;
    @BindView(R.id.time)
    EditText time;
    @BindView(R.id.next)
    Button next;

    private UNIACCOUNT uniaccount;
    private SchoolURL schoolURL;

    List<Category> sexList=null;
    List<Category> identifyList=null;

    private int[] status={0,0,0,0,0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_perfect);
        ButterKnife.bind(this);
        initActionBar();
        initEditListener();
        uniaccount=(UNIACCOUNT) getIntent().getSerializableExtra(ACCOUNTINFO);
        schoolURL=(SchoolURL) getIntent().getSerializableExtra(SCHOOLURL);
        initData();

    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("信息修改");
        setDisplayHomeAsUpEnabled(true);
    }
    /**
     * 初始化监听
     */
    private void initEditListener(){
        college.setOnFocusChangeListener(this);
        number.setOnFocusChangeListener(this);
        name.setOnFocusChangeListener(this);
        academy.setOnFocusChangeListener(this);
        profession.setOnFocusChangeListener(this);
        sex.setOnFocusChangeListener(this);
        identify.setOnFocusChangeListener(this);
        time.setOnFocusChangeListener(this);
    }
    /**
     * 初始化数据
     */
    private void initData(){
        setTextViewContent(college,schoolURL.getSchoolName(),0);
        setTextViewContent(number,uniaccount.getSzPID(),1);
        setTextViewContent(name,uniaccount.getSzTrueName(),2);
        setTextViewContent(academy,"",3);
        setTextViewContent(profession,uniaccount.getSzMajorName(),4);
        setTextViewContent(sex,uniaccount.getDwSex(),5);
        setTextViewContent(identify,"",6);
        setTextViewContent(time,uniaccount.getDwEnrolYear(),7);

        sexList = new ArrayList<>();
        sexList.add(new Category(1, "男"));
        sexList.add(new Category(2, "女"));
        identifyList=new ArrayList<>();
        identifyList.add(new Category(1,"本科生"));
        identifyList.add(new Category(2,"研究生"));
    }


    @OnClick({R.id.next,R.id.time,R.id.sex,R.id.identify})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.next:

                BindSuccessActivity.start(this);
                break;
            case R.id.time:
                Log.d("time click","showDialog");
                showDialog(DIALOG_DATE);
                break;
            case R.id.sex:
                onSinglePicker(sexList, new SinglePicker.OnItemPickListener<Category>() {
                    @Override
                    public void onItemPicked(int index, Category item) {
                        sex.setText(item.getName());
                    }
                });
                break;
            case R.id.identify:
                onSinglePicker(sexList, new SinglePicker.OnItemPickListener<Category>() {
                    @Override
                    public void onItemPicked(int index, Category item) {
                        sex.setText(item.getName());
                    }
                });
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        EditText editText=null;
        int which=0;
        switch(view.getId()){
            case R.id.college:
                editText=college;
                which=0;
                break;
            case R.id.number:
                editText=number;
                which=1;
                break;
            case R.id.name:
                editText=name;
                which=2;
                break;
            case R.id.academy:
                editText=academy;
                which=3;
                break;
            case R.id.profession:
                editText=profession;
                which=4;
                break;
            case R.id.sex:
                editText=sex;
                which=5;
                break;
            case R.id.identify:
                editText=identify;
                which=6;
                break;
            case R.id.time:
                editText=time;
                which=7;
                break;
        }
        if(editText!=null){
            if(checkEditTextContent(editText.getText().toString(),which))
                editText.setTextColor(getResources().getColor(R.color.normal_text));
            else
                editText.setTextColor(getResources().getColor(R.color.red1));
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id) {
            case DIALOG_DATE:
                int year,month,day;
                if (time.getText().toString() != null && time.getText().toString().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                    year = Integer.valueOf(time.getText().toString().substring(0,3));
                    month = Integer.valueOf(time.getText().toString().substring(5,6));
                    day = Integer.valueOf(time.getText().toString().substring(8,9));
                }
                else {
                    Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);
                }
                DatePickerDialog dp = new DatePickerDialog(this, dateChangeListener, year, month, day);
                return dp;
        }
        return null;
    }

    DatePickerDialog.OnDateSetListener dateChangeListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            time.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }
    };

    private void setTextViewContent(EditText textView,String content,int which){
        if(which<0||which>7)
            return;
        if(content!=null&&!content.trim().isEmpty()){
            textView.setText(content.trim());
            textView.setTextColor(getResources().getColor(R.color.normal_text));
            status[which]=1;
        }
        else{
            textView.setText("");
            textView.setTextColor(getResources().getColor(R.color.red1));
            status[which]=2;
        }
    }

    private boolean checkEditTextContent(String content,int which){
        if(which<0||which>7)
            return false;
        if(content!=null&&!content.trim().isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *选择框显示
     */
    public void onSinglePicker(List<Category> data, SinglePicker.OnItemPickListener<Category> listener) {
        SinglePicker<Category> picker = new SinglePicker<>(this, data);
        picker.setCanceledOnTouchOutside(true);
        picker.setSelectedIndex(0);
        picker.setCycleDisable(true);
        //设置大小
        picker.setTextSize(ResourceUtils.getXmlDef(this, R.dimen.picker_content_size));
        picker.setCancelTextSize(ResourceUtils.getXmlDef(this, R.dimen.picker_cancel_size));
        picker.setSubmitTextSize(ResourceUtils.getXmlDef(this, R.dimen.picker_submit_size));
        picker.setOnItemPickListener(listener);

                /*new SinglePicker.OnItemPickListener<Category>() {
            @Override
            public void onItemPicked(int index, Category item) {
                Utils.showToast("index=" + index + ", id=" + item.getId() + ", name=" + item.getName());
            }
        });*/
        picker.show();
    }
}
