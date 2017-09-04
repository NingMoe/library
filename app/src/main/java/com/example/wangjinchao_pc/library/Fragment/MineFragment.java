package com.example.wangjinchao_pc.library.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.activity.BindCollegeActivity;
import com.example.wangjinchao_pc.library.activity.InformationManageActivity;
import com.example.wangjinchao_pc.library.activity.MipcaActivityCapture;
import com.example.wangjinchao_pc.library.activity.RecommendActivity;
import com.example.wangjinchao_pc.library.activity.ModifyPasswdActivity;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by wangjinchao-PC on 2017/7/6.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener{

    View view;

    @BindView(R.id.information_container)
    LinearLayout information_container;
    @BindView(R.id.bind_container)
    LinearLayout bind_container;
    @BindView(R.id.order_container)
    LinearLayout order_container;
    @BindView(R.id.score_container)
    LinearLayout score_container;
    @BindView(R.id.logout)
    Button logout;
    @BindView(R.id.password_reset_container)
    LinearLayout password_reset_container;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).from(getContext()).inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this,view);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }

    @OnClick({R.id.information_container, R.id.bind_container,R.id.order_container,R.id.score_container,R.id.password_reset_container,R.id.logout})
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.information_container:

                InformationManageActivity.start(view.getContext());
                break;
            case R.id.bind_container:

                BindCollegeActivity.start(getContext());
                break;
            case R.id.order_container:

                break;
            case R.id.score_container:

                break;
            case R.id.password_reset_container:
                ModifyPasswdActivity.start(getContext());
                break;
            case R.id.logout:
                //RecommendActivity.start(view.getContext());
                break;
        }
    }
}
