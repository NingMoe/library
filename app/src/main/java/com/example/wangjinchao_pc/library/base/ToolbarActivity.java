package com.example.wangjinchao_pc.library.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.util.Utils;


/**
 * Created by hongzhiyuanzj on 2017/4/21.
 */
public abstract class ToolbarActivity extends BaseActivity {

    Toolbar toolbar;
    private TextView title;
    private ActionBar actionBar;
    private LayoutInflater inflater;
    private View contentView;
    private LinearLayout containerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(this);
        initToolbar();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {

        contentView = inflater.inflate(layoutResID, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        containerView.addView(contentView, params);
        setContentView(containerView);
    }

    private void initToolbar(){
        View view = inflater.inflate(R.layout.toolbar, null);
        containerView = (LinearLayout)view.findViewById(R.id.container);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        title = (TextView)view.findViewById(R.id.title);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setNavigationIcon(Utils.setDrawableTint(toolbar.getNavigationIcon(), getResources().getColor(R.color.textIcon)));
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    public View getContentView(){
        return contentView;
    }

    public void setTitle(int resId){
        title.setText(resId);
    }

    public void setTitle(String s) {title.setText(s);}

    public void setDisplayHomeAsUpEnabled(boolean displayHomeAsUpEnabled){
        actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
    }


}
