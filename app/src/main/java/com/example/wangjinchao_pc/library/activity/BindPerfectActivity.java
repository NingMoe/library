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

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangjinchao-PC on 2017/7/13.
 */

public class BindPerfectActivity extends ToolbarActivity implements View.OnClickListener{

    public static void start(Context context){
        Intent intent =new Intent(context,BindPerfectActivity.class);
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
    EditText prodession;
    @BindView(R.id.sex)
    EditText sex;
    @BindView(R.id.identify)
    EditText identify;
    @BindView(R.id.time)
    EditText time;
    @BindView(R.id.next)
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_perfect);
        ButterKnife.bind(this);
        initActionBar();
    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("信息修改");
        setDisplayHomeAsUpEnabled(true);
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
                new AlertDialog.Builder(this).setSingleChoiceItems(new String[]{"男", "女"}, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0)
                                    sex.setText("男");
                                else
                                    sex.setText("女");
                                dialogInterface.dismiss();
                            }
                        }).show();
                break;
            case R.id.identify:
                new AlertDialog.Builder(this).setSingleChoiceItems(new String[]{"本科生", "研究生"}, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0)
                                    identify.setText("本科生");
                                else
                                    identify.setText("研究生");
                                dialogInterface.dismiss();
                            }
                        }).show();
                break;
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

}
