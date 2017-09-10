package com.example.wangjinchao_pc.library.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.BaseFragment;
import com.example.wangjinchao_pc.library.util.Utils;
import com.jauker.widget.BadgeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/6.
 */

public class OrderFragment extends BaseFragment implements View.OnClickListener{
    View view;

    @BindView(R.id.activity_order)
    TextView activity_order;
    @BindView(R.id.class_order)
    TextView class_order;
    @BindView(R.id.place_order)
    TextView place_order;
    @BindView(R.id.activity_order_container)
    LinearLayout activity_order_container;
    @BindView(R.id.class_order_container)
    LinearLayout class_order_container;
    @BindView(R.id.place_order_container)
    LinearLayout place_order_container;

    @BindView(R.id.activity_number)
    TextView activity_number;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_order, null);
        ButterKnife.bind(this, view);

        //设置监听
        activity_order_container.setOnClickListener(this);
        class_order_container.setOnClickListener(this);
        place_order_container.setOnClickListener(this);

        activity_number.setText("3");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*view = inflater.inflate(R.layout.fragment_order, null);
        ButterKnife.bind(this,view);

        //设置监听
        activity_order_container.setOnClickListener(this);
        class_order_container.setOnClickListener(this);
        place_order_container.setOnClickListener(this);*/
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.activity_order_container:

                break;
            case R.id.class_order_container:

                break;
            case R.id.place_order_container:

                break;
        }
    }

    public void setViewNumber(View v,int number){
        if(v==null){
            Log.d("setNoticeNumber","(View)notice==null");
            return;
        }
        BadgeView badgeView = new BadgeView(this.getActivity().getApplicationContext());
        badgeView.setTargetView(v);
        badgeView.setBadgeGravity(Gravity.CENTER);
        badgeView.setText(""+number);
        badgeView.setTextSize(10);
    }
}
