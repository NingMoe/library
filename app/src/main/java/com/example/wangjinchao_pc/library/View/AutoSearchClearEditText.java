package com.example.wangjinchao_pc.library.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.wangjinchao_pc.library.R;

/**
 * Created by wangjinchao-PC on 2017/7/17.
 */

public class AutoSearchClearEditText extends AppCompatEditText implements View.OnFocusChangeListener,View.OnKeyListener {

    /** 清空图标 **/
    private Drawable mClearDrawable;

    /** 是否获得焦点 **/
    private boolean hasFocus;

    /**
     * 是否点击软键盘搜索
     */
    private boolean pressSearch = false;

    /**
     * 搜索时调用
     */
    private OnSearchClickListener listener;

    public void setOnSearchClickListener(OnSearchClickListener listener) {
        this.listener = listener;
    }

    public interface OnSearchClickListener {
        void onSearchClick(View view);
    }


    public AutoSearchClearEditText(Context context, AttributeSet attrs,
                                   int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AutoSearchClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public AutoSearchClearEditText(Context context) {
        this(context, null);
    }

    private void init() {
        // 获取EditText最右侧的删除图标
        mClearDrawable = this.getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.ic_cancel_black_24dp);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());
        // 默认右侧删除图标不可见
        setClearIconVisible(false);

        //监听
        setOnFocusChangeListener(this);
        setOnKeyListener(this);
    }

    /**
     *
     * 设置右侧的删除图标是否可见
     *
     * @param visible
     */
    public void setClearIconVisible(boolean visible) {
        Drawable drawable = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], drawable, getCompoundDrawables()[3]);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            // 有焦点时焦点，当EditText里的内容长度>0时，显示图标，否则隐藏图标
            setClearIconVisible(getText().length() > 0);
        } else {
            // 失去焦点，不显示清空图标
            setClearIconVisible(false);
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore,
                              int lengthAfter) {
        Log.i("tag", "textChanged");
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                // 判断是否触摸的为右侧图标
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < (getWidth() + getTotalPaddingRight()));
                if (touchable) {
                    // 如果触摸了右侧图标，清空文字
                    setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d("wjc","onkey0");
        pressSearch = (keyCode == KeyEvent.KEYCODE_ENTER);
        Log.d("wjc","onkey1");
        if (pressSearch && listener != null) {
            /*隐藏软键盘*/
            Log.d("wjc","onkey2");
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
            if (event.getAction() == KeyEvent.ACTION_UP) {
                listener.onSearchClick(v);
            }
        }
        return false;
    }
}
