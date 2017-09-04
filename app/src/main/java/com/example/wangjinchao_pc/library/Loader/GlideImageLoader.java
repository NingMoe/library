package com.example.wangjinchao_pc.library.Loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by wangjinchao-PC on 2017/7/19.
 */

//图片加载器——轮播
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        //具体方法内容自己去选择，次方法第三方包，所以将这个权限开放给使是为了减少banner过多的依赖用者去选择
        Glide.with(context.getApplicationContext())
                .load(path)
                .crossFade()
                .into(imageView);
    }
}
