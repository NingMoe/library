package com.example.wangjinchao_pc.library.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.activity.CommentActivity;
import com.example.wangjinchao_pc.library.util.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/9.
 */

public class BookDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //顶部描述类型
    public int TOP_TYPE=12345;
    //CommentViewHolder
    public static String PERSOM_PATH="person_path";
    public static String NAME="name";
    public static String COMMENT_RANK="comment_rank";
    public static String SCORE="score";
    public static String CONTENT="content";
    public static String TIME="time";

    public static String BOOK_PATH="book_path";
    public static String TITLE="title";
    public static String AUTHOR="author";
    public static String COMMEND_NUMBER="comment_number";
    public static String READ_NUMBRT="read_number";
    public static String SIZE="size";
    public static String IS_ZAN="is_zan";
    public static String ZAN_NUMBER="zan_number";
    public static String INTRODUCE="introduce";

    //书籍类型
    private int style;
    //是否为荐购
    private int which;


    private Context context;
    private List<HashMap<String,Object>> mDatas;
    private HashMap<String,Object> top_datas;

    public BookDetailAdapter(Context context, List<HashMap<String, Object>> mDatas,int style,int which) {
        //Log.d("BookCommentAdapter","创建");
        this.context = context;
        this.mDatas = mDatas;
        this.style=style;
        this.which=which;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return TOP_TYPE;
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size()+1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TOP_TYPE)
            return new BookDetailAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_book_detail_item1, null));
        return new BookDetailAdapter.CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_book_detail_item2, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Log.d("BookCommentAdapter","绑定ViewHolder");
        if(position==0){
            set_top((ViewHolder)holder);
        }else
            set_commtent((CommentViewHolder)holder,position);
    }

    void set_top(final ViewHolder holder){
        top_datas=new HashMap<String,Object>();
        top_datas.put(IS_ZAN,false);

        if(style==BookAdapter.PAPER){
            holder.size.setVisibility(View.GONE);
            //设置
            holder.read_btn.setText("库存32本");
            holder.read_btn.setEnabled(false);
        }
        else{
            holder.size.setVisibility(View.VISIBLE);
            holder.size.setText("3245K");
            //设置
            holder.read_btn.setText("开始阅读");
            holder.read_btn.setEnabled(true);

        }

        if(which==BookAdapter.RECOMMEND){
            holder.read_btn.setText("荐购期间");
            holder.read_btn.setEnabled(false);
        }

        /*holder.book_img.setImageURI((String)top_datas.get(BOOK_PATH));
        holder.title.setText((String)top_datas.get(TITLE));
        holder.author.setText((String)top_datas.get(AUTHOR));
        holder.score.setText((String)top_datas.get(SCORE));
        holder.comment_number.setText((String)top_datas.get(COMMEND_NUMBER));
        holder.read_number.setText((String)top_datas.get(READ_NUMBRT));
        holder.size.setText((String)top_datas.get(SIZE));*/
        /*if((boolean)top_datas.get(IS_ZAN))
            holder.zan_btn.setBackgroundColor(context.getResources().getColor(R.color.ic_grey));
        else
            holder.zan_btn.setBackgroundColor(context.getResources().getColor(R.color.yellow1));*/

        /*holder.zan_btn.setText("点赞("+top_datas.get(ZAN_NUMBER)+"人)");
        holder.introduce.setText((String)top_datas.get(INTRODUCE));*/

        /*set_star(holder,(int)top_datas.get(COMMENT_RANK));*/

        /*//针对是否有评论
        if(getItemCount()==1)
            holder.have_comment.setVisibility(View.VISIBLE);
        else
            holder.have_comment.setVisibility(View.GONE);*/

        //监听点击
        holder.zan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BookDetailAdapter","点击zan");
                /*if(!(boolean)top_datas.get(IS_ZAN))
                    holder.zan_btn.setBackgroundTintList(ColorStateList.valueOf(R.coloric_grey));
                else
                    holder.zan_btn.setBackgroundColor(context.getResources().getColor(R.color.yellow1));
                top_datas.put(IS_ZAN,!(boolean)top_datas.get(IS_ZAN));*/
            }
        });
        holder.read_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentActivity.start(context);
            }
        });

    }

    void set_commtent(CommentViewHolder holder, int position){

        Uri uri;
        if(mDatas.get(position-1).get(PERSOM_PATH).equals(null))
            uri=Uri.parse("");
        else
            uri=Uri.parse((String)mDatas.get(position-1).get(PERSOM_PATH));
        Glide.with(context)
                .load(uri)
                .crossFade(300)
                .placeholder(R.mipmap.headphoto)
                .into(holder.person_img);

        holder.name.setText((String)mDatas.get(position-1).get(NAME));
        holder.score.setText((String)mDatas.get(position-1).get(SCORE));
        holder.content.setText((String)mDatas.get(position-1).get(CONTENT));
        holder.time.setText((String)mDatas.get(position-1).get(TIME));
        set_star(holder,(int)mDatas.get(position-1).get(COMMENT_RANK));
    }

    //设置star星星颜色
    public void set_star(BookDetailAdapter.CommentViewHolder holder, int code){
        switch(code){
            case 5:
                /*holder.star_55.setImageDrawable(Utils.setDrawableTint(holder.star_55.getDrawable().mutate(),
                        context.getResources().getColor(R.color.red)));*/
                holder.star_55.setImageDrawable(context.getResources().getDrawable(R.drawable.star_ok));
            case 4:
                holder.star_44.setImageDrawable(context.getResources().getDrawable(R.drawable.star_ok));
            case 3:
                holder.star_33.setImageDrawable(context.getResources().getDrawable(R.drawable.star_ok));
            case 2:
                holder.star_22.setImageDrawable(context.getResources().getDrawable(R.drawable.star_ok));
            case 1:
                holder.star_11.setImageDrawable(context.getResources().getDrawable(R.drawable.star_ok));
                break;
        }

        switch(code){
            case 0:
                holder.star_11.setImageDrawable(context.getResources().getDrawable(R.drawable.star_no));
            case 1:
                holder.star_22.setImageDrawable(context.getResources().getDrawable(R.drawable.star_no));
            case 2:
                holder.star_33.setImageDrawable(context.getResources().getDrawable(R.drawable.star_no));
            case 3:
                holder.star_44.setImageDrawable(context.getResources().getDrawable(R.drawable.star_no));
            case 4:
                holder.star_55.setImageDrawable(context.getResources().getDrawable(R.drawable.star_no));
                break;
        }
    }

    public void set_star(BookDetailAdapter.ViewHolder holder, int code){
        switch(code){
            case 5:
                holder.star_5.setImageDrawable(context.getResources().getDrawable(R.drawable.star_ok));
            case 4:
                holder.star_4.setImageDrawable(context.getResources().getDrawable(R.drawable.star_ok));
            case 3:
                holder.star_3.setImageDrawable(context.getResources().getDrawable(R.drawable.star_ok));
            case 2:
                holder.star_2.setImageDrawable(context.getResources().getDrawable(R.drawable.star_ok));
            case 1:
                holder.star_1.setImageDrawable(context.getResources().getDrawable(R.drawable.star_ok));
                break;
        }

        switch(code){
            case 0:
                holder.star_1.setImageDrawable(context.getResources().getDrawable(R.drawable.star_no));
            case 1:
                holder.star_2.setImageDrawable(context.getResources().getDrawable(R.drawable.star_no));
            case 2:
                holder.star_3.setImageDrawable(context.getResources().getDrawable(R.drawable.star_no));
            case 3:
                holder.star_4.setImageDrawable(context.getResources().getDrawable(R.drawable.star_no));
            case 4:
                holder.star_5.setImageDrawable(context.getResources().getDrawable(R.drawable.star_no));
                break;
        }
    }


    class CommentViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.person_img) SimpleDraweeView person_img;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.star_11) ImageView star_11;
        @BindView(R.id.star_22) ImageView star_22;
        @BindView(R.id.star_33) ImageView star_33;
        @BindView(R.id.star_44) ImageView star_44;
        @BindView(R.id.star_55) ImageView star_55;
        @BindView(R.id.score) TextView score;
        @BindView(R.id.comment_content) TextView content;
        @BindView(R.id.time) TextView time;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.book_img) SimpleDraweeView book_img;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.author) TextView author;
        @BindView(R.id.star_1) ImageView star_1;
        @BindView(R.id.star_2) ImageView star_2;
        @BindView(R.id.star_3) ImageView star_3;
        @BindView(R.id.star_4) ImageView star_4;
        @BindView(R.id.star_5) ImageView star_5;
        @BindView(R.id.score) TextView score;
        @BindView(R.id.comment_number) TextView comment_number;
        @BindView(R.id.read_number) TextView read_number;
        @BindView(R.id.size) TextView size;
        @BindView(R.id.zan) Button zan_btn;
        @BindView(R.id.begin_read) Button read_btn;
        @BindView(R.id.introduce) TextView introduce;
        @BindView(R.id.want_commemt) Button comment_btn;
        @BindView(R.id.have_comment) TextView have_comment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
