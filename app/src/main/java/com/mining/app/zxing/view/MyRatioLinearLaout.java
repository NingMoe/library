package com.mining.app.zxing.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.wangjinchao_pc.library.R;

/**
 * Created by wangjinchao-PC on 2017/7/21.
 */

public class MyRatioLinearLaout extends LinearLayout {

    //宽与长之比
    private float ratio = -1;

    public MyRatioLinearLaout(Context context) {
        super(context);
    }

    public MyRatioLinearLaout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyRatioLinearLaout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    //获取比例
    private void init(AttributeSet attrs) {

        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.MyRatioLinearLaout);
        ratio = a.getFloat(
                R.styleable.MyRatioLinearLaout_ratio_width_to_height,ratio);
        a.recycle();
        Log.d("wjc","init");
    }

    //重写
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //当比例有设置时，对heightMeasureSpec进行重设
        if(ratio>0){
            // 父容器传过来的宽度方向上的模式
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            // 父容器传过来的高度方向上的模式
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);

            // 父容器传过来的宽度的值
            int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
                    - getPaddingRight();
            // 父容器传过来的高度的值
            int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom()
                    - getPaddingTop();


            height = (int) (width / ratio + 0.5f);

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
