package com.example.wangjinchao_pc.library.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.activity.DebtDetailActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/10.
 */

public class DebtDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int FIRST=100;

    public static String DEBT_SUM="debt_sum";
    public static String TIME="time";
    public static String TITLE="title";
    public static String DEBT_PRICE="debt_price";

    private int type;

    private Context context;
    //内容信息放在一起
    private List<HashMap<String,Object>> mDatas;

    public DebtDetailAdapter(Context context, List<HashMap<String, Object>> mDatas,int type) {
        this.context = context;
        this.mDatas = mDatas;
        this.type=type;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return FIRST;
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==FIRST)
            return new FirstViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_debt_detail_item1, null));
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_debt_detail_item2, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position==0){
            setFirst((FirstViewHolder)holder);
        }else{
            setMajor((ViewHolder) holder,position);
        }
    }

    private void setFirst(FirstViewHolder holder){
        if(mDatas.size()<=1){
            holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.info_success));
            holder.container.setBackgroundColor(context.getResources().getColor(R.color.information_success));
            holder.debt_sum.setText("0");
        }else
            holder.debt_sum.setText((String)mDatas.get(0).get(DEBT_SUM));
        if(type== DebtDetailActivity.TYPE_BOOK)
            holder.unit.setText("本");
        else if(type==DebtDetailActivity.TYPE_PRICE)
            holder.unit.setText("元");


    }

    private void setMajor(ViewHolder holder,int position){
        holder.time.setText((String)mDatas.get(position).get(TIME));
        holder.title.setText((String)mDatas.get(position).get(TITLE));
        holder.debt_price.setText((String)mDatas.get(position).get(DEBT_PRICE));
        if(type== DebtDetailActivity.TYPE_BOOK){
            holder.unit.setText("天");
        }else if(type==DebtDetailActivity.TYPE_PRICE)
            holder.unit.setText("元");
    }



    class FirstViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.debt_sum)
        TextView debt_sum;
        @BindView(R.id.unit)
        TextView unit;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.container)
        LinearLayout container;
        public FirstViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.debt_price)
        TextView debt_price;
        @BindView(R.id.already)
        TextView already;
        @BindView(R.id.unit)
        TextView unit;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
