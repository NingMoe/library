package com.example.wangjinchao_pc.library.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangjinchao_pc.library.Constant.Constant;
import com.example.wangjinchao_pc.library.Constant.Value;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.BaseFragment;
import com.example.wangjinchao_pc.library.enity.domain.Raking;
import com.example.wangjinchao_pc.library.util.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/7.
 */

public class HomePage_Rank_Item_big extends BaseFragment {

    View view;
    @BindView(R.id.campus_rank) TextView campus_rank;
    @BindView(R.id.campus_pic) ImageView campus_pic;
    @BindView(R.id.campus_state) TextView campus_state;
    @BindView(R.id.academy_rank) TextView academy_rank;
    @BindView(R.id.academy_pic) ImageView academy_pic;
    @BindView(R.id.academy_state) TextView academy_state;
    @BindView(R.id.profession_rank) TextView profession_rank;
    @BindView(R.id.profession_pic) ImageView profession_pic;
    @BindView(R.id.profession_state) TextView profession_state;

    int i=0;

    public HomePage_Rank_Item_big(){

    }

    public void setI(int ii){
        i=ii;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage_rank_item_big, null);
        ButterKnife.bind(this,view);
        refreshData();
        return view;
    }

    public void setViewData(List<Raking> tempRakingList){
        Logger.d(this.getClass(),"setViewData"+i);
        Raking raking;
        for(int i=0;i<tempRakingList.size();i++){
            raking=tempRakingList.get(i);
            if(raking.getStatus().equals(Constant.RANK_CAMPUS)){
                campus_rank.setText(raking.getNumber());
                campus_state.setText(raking.getChange_number());
                if(raking.getFlag().equals(Constant.RANK_UP)) {
                    campus_state.setTextColor(getResources().getColor(R.color.rank_up));
                    campus_pic.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_upward_black_24dp));
                }else if(raking.getFlag().equals(Constant.RANK_DOWN)){
                    campus_state.setTextColor(getResources().getColor(R.color.rank_down));
                    campus_pic.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_downward_black_24dp));
                }
            }else if(raking.getStatus().equals(Constant.RANK_ACADEMY)){
                academy_rank.setText(raking.getNumber());
                academy_state.setText(raking.getChange_number());
                if(raking.getFlag().equals(Constant.RANK_UP)) {
                    academy_state.setTextColor(getResources().getColor(R.color.rank_up));
                    academy_pic.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_upward_black_24dp));
                }else if(raking.getFlag().equals(Constant.RANK_DOWN)){
                    academy_state.setTextColor(getResources().getColor(R.color.rank_down));
                    academy_pic.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_downward_black_24dp));
                }
            }else if(raking.getStatus().equals(Constant.RANK_PROFESSION)) {
                profession_rank.setText(raking.getNumber());
                profession_state.setText(raking.getChange_number());
                if (raking.getFlag().equals(Constant.RANK_UP)) {
                    profession_state.setTextColor(getResources().getColor(R.color.rank_up));
                    profession_pic.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_upward_black_24dp));
                } else if (raking.getFlag().equals(Constant.RANK_DOWN)) {
                    profession_state.setTextColor(getResources().getColor(R.color.rank_down));
                    profession_pic.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_downward_black_24dp));
                }
            }
        }
    }

    public void refreshData(){
        switch (i){
            case 1:
                if(Value.isRefresh1())
                    try{
                        setViewData(Value.getRakingList1());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                break;
            case 2:
                if(Value.isRefresh2())
                    try{
                        setViewData(Value.getRakingList2());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                break;
            case 3:
                if(Value.isRefresh3())
                    try{
                        setViewData(Value.getRakingList3());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                break;
            case 4:
                if(Value.isRefresh4())
                    try{
                        setViewData(Value.getRakingList4());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                break;
        }
    }
}
