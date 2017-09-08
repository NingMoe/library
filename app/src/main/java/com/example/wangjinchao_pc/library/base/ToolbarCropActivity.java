package com.example.wangjinchao_pc.library.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.util.Utils;

/**
 * Created by wangjinchao-PC on 2017/7/11.
 */

public class ToolbarCropActivity extends BaseActivity{
    Toolbar toolbar;
    private ActionBar actionBar;
    private LayoutInflater inflater;
    private View contentView;
    private LinearLayout containerView;
    private Button ok_btn;
    private TextView title;

    View.OnClickListener listener;

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
        View view = inflater.inflate(R.layout.toolbar_crop, null);
        containerView = (LinearLayout)view.findViewById(R.id.container);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ok_btn = (Button) view.findViewById(R.id.ok);
        title=(TextView)view.findViewById(R.id.title);
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

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                    listener.onClick(view);
            }
        });
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    public View getContentView(){
        return contentView;
    }

    public void setDisplayHomeAsUpEnabled(boolean displayHomeAsUpEnabled){
        actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setBtnText(String content){
        if(content!=null&&content!="")
            ok_btn.setText(content);
    }

    public void setTitle(String title){
        if(title!=null&&!title.isEmpty()){
            this.title.setText(title);
            this.title.setVisibility(View.VISIBLE);
        }
    }
}
