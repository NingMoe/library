package com.example.wangjinchao_pc.library.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.BaseFragment;

/**
 * Created by wangjinchao-PC on 2017/7/8.
 */

public class HomePage_Rank_Item_small extends BaseFragment {
    View view;
    public HomePage_Rank_Item_small(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage_rank_item_small, null);
        return view;
    }
}