package com.example.wangjinchao_pc.library.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.activity.BindCollegeActivity;
import com.example.wangjinchao_pc.library.activity.InformationManageActivity;
import com.example.wangjinchao_pc.library.activity.ModifyPasswdActivity;
import com.example.wangjinchao_pc.library.activity.SetInformationActivity;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.BaseFragment;
import com.example.wangjinchao_pc.library.util.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.wangjinchao_pc.library.Constant.Configure.PHOTOURL_PREX;

/**
 * Created by wangjinchao-PC on 2017/7/6.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener{

    View view;

    private final int requestCode=5;

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
    @BindView(R.id.headPhoto)
    ImageView headPhoto;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.interest)
    TextView interest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d(this.getClass(),"onCreate");
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).from(getContext()).inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this,view);

        initView();
    }

    private void initView(){
        Logger.d(this.getClass(),"initView");
        Glide.with(this)
                .load(PHOTOURL_PREX+ MyApplication.getToken().getAccount()+".jpg")
                .crossFade(0)
                .placeholder(R.mipmap.headphoto)
                .into(headPhoto);
        String temp=MyApplication.getUser().getName();
        if(temp!=null&&!temp.isEmpty())
            name.setText(temp);
        temp=MyApplication.getUser().getHobbyName();
        if(temp!=null&&!temp.isEmpty())
            interest.setText(temp);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d(this.getClass(),"onCreateView");
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
                Intent intent = new Intent();
                intent.setClass(this.getContext(), ModifyPasswdActivity.class);
                startActivityForResult(intent,requestCode);
                /*ModifyPasswdActivity.start(getContext());*/
                break;
            case R.id.logout:
                //RecommendActivity.start(view.getContext());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.getActivity().RESULT_OK) {
            if(this.requestCode==requestCode)
                initView();
        }
    }
}
