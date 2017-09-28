package com.example.wangjinchao_pc.library.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.activity.BookDetailActivity;
import com.example.wangjinchao_pc.library.util.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/9.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    public static String NUMBER="number";
    public static String TITLE="title";
    public static String BOOK_IMG="book_img";
    public static String AUTHOR="author";
    public static String IS_COLLECT="is_collect";
    public static String COLLECT_NUMBER="collect_number";
    public static String CONTENT="content";
    public static String PRICE="price";
    public static String STYLE="style";
    public static String WHICH="WHICH";

    public final static int PAPER=1;  //纸质书
    public final static int ELECT=2;  //电子书

    private static String STYLE_PAPER="纸质书";
    private static String STYLE_ELECT="电子书";

    public static int RECOMMEND=1;
    public static int COMMON=2;

    private int which;    //代表是书籍详情还是荐购书籍详情(默认为书籍详情)------作用，传给详情活动

    private Context context;
    private List<HashMap<String,Object>> mDatas;

    public BookAdapter(Context context, List<HashMap<String, Object>> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        this.which=COMMON;
    }

    public BookAdapter(Context context, List<HashMap<String, Object>> mDatas, int which) {
        this.context = context;
        this.mDatas = mDatas;
        this.which=which;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.search_book_item, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d("onBindViewHolder",position+"");

        //监听收藏
        holder.collect_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BookAdapter","点击收藏");

                if(!(Boolean)mDatas.get(position).get(IS_COLLECT))
                    holder.is_collect.setImageDrawable(Utils.setDrawableTint(holder.is_collect.getDrawable().mutate(),
                            context.getResources().getColor(R.color.red)));  ///????????????????????
                else
                    holder.is_collect.setImageDrawable(Utils.setDrawableTint(holder.is_collect.getDrawable().mutate(),
                            context.getResources().getColor(R.color.grey3)));
                mDatas.get(position).put(IS_COLLECT,!(Boolean)mDatas.get(position).get(IS_COLLECT));
            }
        });
        //监听详情
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BookAdapter","点击进入详情");

                BookDetailActivity.start(context,(int)mDatas.get(position).get(STYLE),which);
            }
        });

        holder.number.setText(position+1+"");
        holder.title.setText((String)mDatas.get(position).get(TITLE));

        /*if(mDatas.get(position).get(BOOK_IMG)!=null){
            Log.d("bookaboutAdapter",""+position+" "+(String)mDatas.get(position).get(BOOK_IMG));
            Glide.with(context)
                    .load(Uri.parse((String)mDatas.get(position).get(BOOK_IMG)))
                    .crossFade(300)
                    .placeholder(R.drawable.ic_wallpaper_black_24dp)
                    .into(holder.book_img);

*//*            holder.book_img.setImageURI((String)mDatas.get(position).get(BOOK_IMG));*//*
        }*/
        Uri uri;
        if((String)mDatas.get(position).get(BOOK_IMG)==null)
            uri=Uri.parse("");
        else
            uri=Uri.parse((String)mDatas.get(position).get(BOOK_IMG));

        Glide.with(context)
                .load(uri)
                .crossFade(300)
                .placeholder(context.getResources().getDrawable(R.drawable.ic_wallpaper_black_24dp))
                .into(holder.book_img);

        holder.author.setText((String)mDatas.get(position).get(AUTHOR));
        if((Boolean)mDatas.get(position).get(IS_COLLECT))
            holder.is_collect.setImageDrawable(Utils.setDrawableTint(holder.is_collect.getDrawable().mutate(),
                    context.getResources().getColor(R.color.red)));  ///????????????????????
        else
            holder.is_collect.setImageDrawable(Utils.setDrawableTint(holder.is_collect.getDrawable().mutate(),
                    context.getResources().getColor(R.color.grey3)));
        holder.collect_number.setText((String)mDatas.get(position).get(COLLECT_NUMBER));
        holder.content.setText((String)mDatas.get(position).get(CONTENT));
        holder.price.setText((String)mDatas.get(position).get(PRICE));

        if((int)mDatas.get(position).get(STYLE)==PAPER)
            holder.style.setText(STYLE_PAPER);
        else if((int)mDatas.get(position).get(STYLE)==ELECT)
            holder.style.setText(STYLE_ELECT);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.number)TextView number;
        @BindView(R.id.title)TextView title;
        @BindView(R.id.book_photo)ImageView book_img;
        @BindView(R.id.author)TextView author;
        @BindView(R.id.collect_container)LinearLayout collect_container;
        @BindView(R.id.is_collect)ImageView is_collect;
        @BindView(R.id.collect_number)TextView collect_number;
        @BindView(R.id.content)TextView content;
        @BindView(R.id.price)TextView price;
        @BindView(R.id.style)TextView style;
        @BindView(R.id.container)LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
