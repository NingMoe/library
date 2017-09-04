package com.example.wangjinchao_pc.library.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.FrameLayout;

import com.example.wangjinchao_pc.library.View.LoadView;


/**
 * Created by hongzhiyuanzj on 2017/4/20.
 */
public class BaseFragment extends Fragment {
    static{
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private LoadView loadView;

    public View getLoadView(){
        return loadView.getLoadView();
    }

    protected void startLoading(){
        loadView.startLoading();
    }

    protected void stopLoading(){
        loadView.stopLoading();
    }

    protected FrameLayout.LayoutParams getLayoutParams(){ return loadView.getLayoutParams(); }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loadView = new LoadView(context);
    }

    protected void setLoadView(FrameLayout frameLayout){
        frameLayout.addView(getLoadView(), getLayoutParams());
    }

}
