package com.example.wangjinchao_pc.library.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class MyViewPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private FragmentManager fm;

    public MyViewPageAdapter(List<Fragment> fragments, FragmentManager fm) {
        super(fm);
        this.fragments = fragments;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }


}
