package com.example.wangjinchao_pc.library.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.example.wangjinchao_pc.library.Constant.Configure;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.enity.Token;
import com.example.wangjinchao_pc.library.enity.result.User;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hongzhiyuanzj on 2017/6/5.
 */

public class FileHelper {

    public static final String AD_IMAGE="adimage.jpg";

    public static final String TOKEN="token";


    public static FileHelper instance;
    private Context context;

    private FileHelper(){
        context = MyApplication.getContext();
    }

    public static FileHelper getInstance(){

        if(instance==null){
            instance = new FileHelper();
        }
        return instance;
    }

    //存放Token
    public static void saveToken(Context context,Token token){
        SharedPreferences.Editor editor=context.getSharedPreferences(Configure.CONFIGURE, Context.MODE_PRIVATE).edit();
        editor.putString("account",token.getAccount());
        editor.putString("password",token.getPassword());
        editor.commit();
    }
    //取Token
    public static Token getToken(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(Configure.CONFIGURE, Context.MODE_PRIVATE);
        return new Token(sharedPreferences.getString("account",""),sharedPreferences.getString("password",""));
    }


    //头像存储
    public String saveHeadphoto(Bitmap bitmap){
        String path=MyApplication.getToken().getAccount();
        path+=Configure.HEADPHOTO_NAME;
        try {
            FileOutputStream fos = context.openFileOutput(path, Context.MODE_PRIVATE);
            if(bitmap==null){
                Log.e("bitmap","isnull");
                return "";
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return path;
    }

    public Uri getHeadphoto(boolean isTemp){
        String path=MyApplication.getToken().getAccount();
        if(isTemp)
            path+=Configure.HEADPHOTO_TEMP_NAME;
        else
            path+=Configure.HEADPHOTO_NAME;
        File root = context.getFilesDir();
        File img = new File(root, path);
        return Uri.fromFile(img);
    }
    public void saveHeadphoto(Uri uri){
        saveHeadphoto(ratio(uri, 200, 200));
    }

    //广告图片存储
    public void saveAd_Image(Bitmap bitmap){
        try {
            FileOutputStream fos = context.openFileOutput(AD_IMAGE, Context.MODE_PRIVATE);
            if(bitmap==null){
                Log.e("bitmap","isnull");
                return;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //??大小
    public void saveAd_Image(Uri uri){
        saveHeadphoto(ratio(uri, 200, 200));
    }

    public Uri getAd_Image(){
        File root = context.getFilesDir();
        File img = new File(root, AD_IMAGE);
        return Uri.fromFile(img);
    }

    public void clearHeadphote_Cache(){
        ImagePipeline pipeline = Fresco.getImagePipeline();
        pipeline.evictFromCache(Uri.fromFile(new File(context.getFilesDir(), User.getUserId()+Configure.HEADPHOTO_NAME)));
    }

    public void clearAdImage_Cache(){
        ImagePipeline pipeline = Fresco.getImagePipeline();
        pipeline.evictFromCache(Uri.fromFile(new File(context.getFilesDir(),AD_IMAGE)));
    }

    public Bitmap ratio(Uri uri, int width, int height) {

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            BitmapFactory.decodeStream(inputStream, null, options);

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int w = options.outWidth;
            int h = options.outHeight;

            int ratio = 1;
            if(w>h){
                ratio = (int)(h*1f/height);
            }else{
                ratio = (int)(w*1f/width);
            }
            if(ratio<1){
                ratio = 1;
            }
            Log.e("ratio", ratio+"");

            options.inSampleSize = ratio;
            options.inJustDecodeBounds = false;

            inputStream = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
