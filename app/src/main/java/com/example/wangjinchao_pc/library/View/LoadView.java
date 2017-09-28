package com.example.wangjinchao_pc.library.View;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.wangjinchao_pc.library.R;

/**
 * Created by hongzhiyuanzj on 2017/4/20.
 */
public class LoadView {

    private View loadView;
    private Context context;
    private boolean isVisiable;
    private AnimationDrawable animationDrawable;

    private ImageView load_img;

    public LoadView(Context context){
        this.context = context;
        loadView = LayoutInflater.from(context).inflate(R.layout.view_load,null);
        load_img = (ImageView)loadView.findViewById(R.id.load_img);
        loadView.setVisibility(View.GONE);
        animationDrawable = (AnimationDrawable)load_img.getDrawable();
    }
    public void startLoading(){
        animationDrawable.start();
        loadView.setVisibility(View.VISIBLE);
    }
    public void stopLoading(){
        animationDrawable.stop();
        loadView.setVisibility(View.GONE);
    }

    public View getLoadView(){
        return loadView;
    }
    public FrameLayout.LayoutParams getLayoutParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        return params;
    }
}
