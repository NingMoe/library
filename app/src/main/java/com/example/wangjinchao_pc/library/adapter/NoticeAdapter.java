package com.example.wangjinchao_pc.library.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.activity.NoticeInfoActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/12.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    public static String TIME="time";
    public static String CONTENT="content";
    public static String IMG="img";

    private Context context;
    private List<HashMap<String,Object>> mDatas;

    public NoticeAdapter(Context context, List<HashMap<String, Object>> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_mine_notice_item, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.time.setText((String)mDatas.get(position).get(TIME));
        holder.content.setText((String)mDatas.get(position).get(CONTENT));
        //设置img

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoticeInfoActivity.start(context,mDatas.get(position));
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.img)
        ImageView img;

        @BindView(R.id.container)
        LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
