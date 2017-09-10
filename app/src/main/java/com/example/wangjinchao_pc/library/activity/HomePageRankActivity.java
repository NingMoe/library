package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.wangjinchao_pc.library.Fragment.HomePage_Rank_Item_big;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.adapter.MyViewPageAdapter;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinchao-PC on 2017/7/8.
 */

public class HomePageRankActivity extends ToolbarActivity {

    public static void start(Context context){
        Intent intent=new Intent(context,HomePageRankActivity.class);
        context.startActivity(intent);
    }
    //对于排名显示的控制
    ViewPager viewPager;
    TabLayout tabLayout;
    //viewPager的子页
    private Fragment rank_item1,rank_item2,rank_item3,rank_item4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_rank);
        initActionBar();
        init_View();
        init();
    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("我的排名");
        setDisplayHomeAsUpEnabled(true);
    }
    /**
     * 初始化View视图
     */
    void init_View(){
        tabLayout=(TabLayout)findViewById(R.id.rank_tabLayout);
        viewPager=(ViewPager)findViewById(R.id.rank_viewPager);
    }

    //初始化数据以及排名显示的控制---------------???
    void init(){

        //将子页加入ViewPage
        List<Fragment> fragmentList = new ArrayList<>();
        rank_item1=new HomePage_Rank_Item_big();
        fragmentList.add(rank_item1);
        rank_item2=new HomePage_Rank_Item_big();
        fragmentList.add(rank_item2);
        rank_item3=new HomePage_Rank_Item_big();
        fragmentList.add(rank_item3);
        rank_item4=new HomePage_Rank_Item_big();
        fragmentList.add(rank_item4);

        viewPager.setAdapter(new MyViewPageAdapter(fragmentList,getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("借阅数量");
        tabLayout.getTabAt(1).setText("进馆次数");
        tabLayout.getTabAt(2).setText("预约数量");
        tabLayout.getTabAt(3).setText("综合排名");

    }
}
