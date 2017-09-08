package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.base.ToolbarCropActivity;
import com.example.wangjinchao_pc.library.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/21.
 */

public class SetInformationActivity extends ToolbarActivity {
    public static void start(Context context){
        Intent intent=new Intent(context,SetInformationActivity.class);
        context.startActivity(intent);
    }

    public static final String CONTENT="CONTENT";

    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.tip)
    TextView tip;

    private MenuItem scanner;
    private MenuItem search;
    private MenuItem recommend;
    private MenuItem notice;

    String[] tips;
    String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setinformation);
        ButterKnife.bind(this);
        init();

        /*//监听toolbar上的按钮
        setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult();
            }
        });*/

    }

    /**
     * 初始化
     */
    private void init(){
        tips=getResources().getStringArray(R.array.information_modify_tip);
        titles=getResources().getStringArray(R.array.information_modify_title);
        /*setBtnText("保存");*/
        initActionBar(getIntent().getIntExtra(InformationManageActivity.STYLE,0));
        setAllText(getIntent().getIntExtra(InformationManageActivity.STYLE,0),
                getIntent().getStringExtra(InformationManageActivity.CONTENT));

        //设置光标
        content.setSelection(content.getText().length());
    }
    /**
     * 初始化导航栏
     */
    private void initActionBar(int style){
        setTitle(titles[style]);
        setDisplayHomeAsUpEnabled(true);
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

    /**
     * 创建菜单栏
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("Activity", "onCreateOptionMenu");
        getMenuInflater().inflate(R.menu.zxing, menu);
        scanner = menu.findItem(R.id.start_zxing);
        search = menu.findItem(R.id.search);
        recommend=menu.findItem(R.id.start_recommend);
        notice=menu.findItem(R.id.start_notice);
        setMenuItem(search,false);
        setMenuItem(scanner,false);
        setMenuItem(notice,false);
        setMenuItem(recommend,true);
        recommend.setTitle("保存");
        return true;
    }
    /**
     * 菜单栏监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("Activity", "onOptionsItemSelected");

        switch (item.getItemId()) {
            case R.id.start_zxing:

                break;
            case R.id.search:

                break;

            case R.id.start_notice:

                break;
            case R.id.start_recommend:
                setResult();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //判断menuItem是否为空，减小冗余
    public void setMenuItem(MenuItem menuItem,boolean flag){
        if(menuItem==null)
            return;
        menuItem.setVisible(flag);
    }
}
