package com.example.wangjinchao_pc.library.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.BaseActivity;
import com.example.wangjinchao_pc.library.application.MyApplication;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hongzhiyuanzj on 2017/4/21.
 */
public class Utils {
    public static List<HashMap<String,Object>> createData(String[] keys, String[] values , int count){
        List<HashMap<String, Object>> data = new ArrayList<>();
        HashMap<String, Object> map;
        for(int i=0;i<count;i++){
            map = new HashMap<>();
            for(int j=0;j<keys.length;j++) {
                map.put(keys[j], values[j]);
            }
            data.add(map);
        }
        return data;
    }

    public static void showToast(String text){
        Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(int resId){
        Toast.makeText(MyApplication.getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    //图片着色
    public static Drawable setDrawableTint(Drawable drawable, ColorStateList colorStateList){
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);   //针对不是vector图片而加
        DrawableCompat.setTintList(wrappedDrawable, colorStateList);
        return wrappedDrawable;
    }

    public static Drawable setDrawableTint(Drawable drawable, int drawid){
        DrawableCompat.setTint(drawable, drawid);
        return drawable;
    }

    static class Pair{
        int start;
        int end;

        public Pair(int start , int end){
            this.start = start;
            this.end = end;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }

    public static SpannableStringBuilder setTextColor(String string){
        if(string==null){
            return new SpannableStringBuilder();
        }
        List<Pair> pairs = getPair(string);
        string = string.replaceAll("\\<em\\>", "").replaceAll("\\</em\\>", "");
        SpannableStringBuilder builder = new SpannableStringBuilder(string);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(MyApplication.getContext().getResources().getColor(R.color.colorPrimary));
        for(int i=0;i<pairs.size();i++){
            Pair pair = pairs.get(i);
            pair.setStart(pair.getStart()-(4+i*9));
            pair.setEnd(pair.getEnd()-(4+i*9));
        }
        for(Pair pair : pairs){

            builder.setSpan(colorSpan, pair.getStart(), pair.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;

    }

    private static List<Pair> getPair(String string){
        int pos = 0;
        List<Pair> lists = new ArrayList<>();
        Pair pair = null;
        while(pos<string.length()){
            int start = string.indexOf("<em>", pos)+4;
            if(start==-1){
                break;
            }
            int end = string.indexOf("</em>", pos);
            if(end==-1){
                break;
            }
            pos = end+1;
            pair = new Pair(start, end);
            lists.add(pair);
        }
        return lists;
    }

//    public static boolean checkLogin(BaseActivity activity){
//        if(!Prefer.isLogin()){
//        //    LoginDialog.showDialog(activity);
//            return false;
//        }
//        return true;
//    }


    public static String transTime(long time, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date(time));
    }
    public static String transTime(long time){
        return transTime(time, "yyyy年MM月dd日");
    }

    public static void hideSoftInput(Activity activity){
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null){
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(),0);
        }
    }

    public static String filterData(String data){
        if(data==null||data.equals("")){
            return MyApplication.getContext().getResources().getString(R.string.none);
        }
        return data;
    }

    public static String getPath(BaseActivity activity, Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
