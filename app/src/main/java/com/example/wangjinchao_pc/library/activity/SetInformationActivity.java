package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.base.ToolbarCropActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/21.
 */

public class SetInformationActivity extends ToolbarCropActivity {
    public static void start(Context context){
        Intent intent=new Intent(context,SetInformationActivity.class);
        context.startActivity(intent);
    }

    public static final String CONTENT="CONTENT";

    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.tip)
    TextView tip;

    String[] tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setinformation);
        ButterKnife.bind(this);
        init();

        //监听toolbar上的按钮
        setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult();
            }
        });

    }

    void init(){
        tips=getResources().getStringArray(R.array.information_modify_tip);
        setBtnText("保存");
        setAllText(getIntent().getIntExtra(InformationManageActivity.STYLE,0),
                getIntent().getStringExtra(InformationManageActivity.CONTENT));

        //设置光标
        content.setSelection(content.getText().length());
    }

    void setAllText(int style,String contents){
        content.setText(contents);
        tip.setText(tips[style]);
    }

    void setResult(){
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT, content.getText().toString());
        resultIntent.putExtras(bundle);
        this.setResult(RESULT_OK, resultIntent);
        SetInformationActivity.this.finish();
    }
}
