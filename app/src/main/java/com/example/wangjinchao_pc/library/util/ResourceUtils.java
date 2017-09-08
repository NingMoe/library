package com.example.wangjinchao_pc.library.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by wangjinchao-PC on 2017/9/8.
 */

public class ResourceUtils {
    private static TypedValue mTmpValue = new TypedValue();

    private ResourceUtils(){}

    public static int getXmlDef(Context context, int id) {
        synchronized (mTmpValue) {
            TypedValue value = mTmpValue;
            context.getResources().getValue(id, value, true);
            return (int) TypedValue.complexToFloat(value.data);
        }
    }
}
