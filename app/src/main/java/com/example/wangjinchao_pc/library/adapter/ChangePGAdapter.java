package com.example.wangjinchao_pc.library.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by wangjinchao-PC on 2017/7/6.
 */

public class ChangePGAdapter extends PagerAdapter {

    Context context;
    ImageView[] mImageViews;
    public ChangePGAdapter(Context context,ImageView[] mImageViews){
        this.context=context;
        this.mImageViews=mImageViews;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
    /**
      * 将图片从缓存中去除
     */
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);
    }

    /**
     * 将图片装入缓存中
     */
    @Override
    public Object instantiateItem(View container, int position) {
        int l=position % mImageViews.length;
        ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
        return mImageViews[l];
    }
}
