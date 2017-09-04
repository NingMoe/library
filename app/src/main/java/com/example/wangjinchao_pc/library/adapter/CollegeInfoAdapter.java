package com.example.wangjinchao_pc.library.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.activity.MainActivity;
import com.example.wangjinchao_pc.library.activity.MipcaActivityCapture;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by wangjinchao-PC on 2017/7/13.
 */

public class CollegeInfoAdapter extends RecyclerView.Adapter<CollegeInfoAdapter.ViewHolder> {

    public static String CONTENT="content";
    private Context context;
    private List<HashMap<String,Object>> mDatas;
    private View.OnClickListener listener;

    public CollegeInfoAdapter(Context context, List<HashMap<String, Object>> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        this.listener=null;
    }

    public CollegeInfoAdapter(Context context, List<HashMap<String, Object>> mDatas, View.OnClickListener listener) {
        this.context = context;
        this.mDatas = mDatas;
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public CollegeInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollegeInfoAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_college_information_item, null));
    }

    @Override
    public void onBindViewHolder(final CollegeInfoAdapter.ViewHolder holder, final int position) {
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener==null)
                    return;

                listener.onClick(holder.content);
            }
        });
        holder.content.setText((String)mDatas.get(position).get(CONTENT));
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.container)
        RelativeLayout container;
        @BindView(R.id.content)
        TextView content;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
