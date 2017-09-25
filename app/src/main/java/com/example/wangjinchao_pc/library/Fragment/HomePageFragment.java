package com.example.wangjinchao_pc.library.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.Constant.Constant;
import com.example.wangjinchao_pc.library.Constant.Value;
import com.example.wangjinchao_pc.library.Loader.GlideImageLoader;
import com.example.wangjinchao_pc.library.View.MyRatioLinearLayout;
import com.example.wangjinchao_pc.library.activity.HomePageRankActivity;
import com.example.wangjinchao_pc.library.adapter.MyViewPageAdapter;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.BaseFragment;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.api.GetRankApi;
import com.example.wangjinchao_pc.library.enity.domain.Raking;
import com.example.wangjinchao_pc.library.enity.baseResult.BaseResultEntity;
import com.example.wangjinchao_pc.library.util.Logger;
import com.example.wangjinchao_pc.library.util.Utils;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/6.
 */

public class HomePageFragment extends BaseFragment implements HttpOnNextListener{

    View view;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.myRatio)
    MyRatioLinearLayout myRatioLinearLayout;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rank_viewPager)
    ViewPager rank_viewPager;
    @BindView(R.id.rank_tabLayout)
    TabLayout rank_tabLayout;
    //viewPager的子页
    private Fragment rank_item1,rank_item2,rank_item3;
    List<Raking> rakingList1,rakingList2,rakingList3,rakingList4;
    //更多
    @BindView(R.id.rank_more)
    LinearLayout rank_more;

    //对于排名显示的控制
    @BindView(R.id.order_viewPager)
    ViewPager order_viewPager;
    @BindView(R.id.order_tabLayout)
    TabLayout order_tabLayout;
    //viewPager的子页
    private Fragment order_item1,order_item2,order_item3;

    private float order_mLastX=0;
    private float rank_mLastX=0;

    private HttpManager httpManager;
    private GetRankApi getRankApi;

    public HomePageFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_homepage, null);
        ButterKnife.bind(this, view);
        initHttp();

        //初始化banner
        banner.setImages(MyApplication.images)
                .setBannerTitles(MyApplication.titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {

                        Toast.makeText(getContext(),"点击了第"+position+"张图片",Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
        //避免滑动冲突
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("wjc","onPageScrollStateChanged");
                switch(state){
                    //正在滑动，一般人为触发
                    case 1:

                        if(swipeRefreshLayout.isEnabled()){
                            swipeRefreshLayout.setEnabled(false);
                        }
                        break;
                    //结束滑动
                    case 2:
                        if(!swipeRefreshLayout.isEnabled()){
                            //恢复刷新功能
                            swipeRefreshLayout.setEnabled(true);
                        }
                        break;
                }
            }
        });

        //初始化
        init_rank();
        init_order();
        //监听more
        rank_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("From HomePageFragment","To HomePageRankActivity");
                HomePageRankActivity.start(view.getContext());
            }
        });

        //监听滑动
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("homepage","onRefresh");
                //刷新
                refreshData();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 初始化状态栏
     */
    private void initHttp(){
        httpManager=new HttpManager(this,(RxAppCompatActivity) getActivity());
        getRankApi=new GetRankApi();
        refreshData();
    }

    private void refreshData(){
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.MONTH, -1);
        Logger.d(this.getClass(),ca.get(Calendar.YEAR)+" "+ca.get(Calendar.MONTH));
        getRankApi.setAllParam(ca.get(Calendar.YEAR)+"",(ca.get(Calendar.MONTH)+1)+"",MyApplication.getToken().getAccount());
        httpManager.doHttpDeal(getRankApi);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }

    //初始化数据以及排名显示的控制---------------???
    void init_rank(){
        //将子页加入ViewPage
        List<Fragment> fragmentList = new ArrayList<>();
        rank_item1=new HomePage_Rank_Item_small();
        ((HomePage_Rank_Item_small)rank_item1).setI(1);
        fragmentList.add(rank_item1);
        rank_item2=new HomePage_Rank_Item_small();
        ((HomePage_Rank_Item_small)rank_item2).setI(2);
        fragmentList.add(rank_item2);
        rank_item3=new HomePage_Rank_Item_small();
        ((HomePage_Rank_Item_small)rank_item3).setI(3);
        fragmentList.add(rank_item3);

        rank_viewPager.setAdapter(new MyViewPageAdapter(fragmentList,this.getChildFragmentManager()));
        //避免滑动冲突
        rank_viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        // 记录点击到ViewPager时候，手指的X坐标
                        rank_mLastX = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 超过阈值，关闭刷新
                        if(Math.abs(motionEvent.getX() - rank_mLastX) > 60f&&swipeRefreshLayout.isEnabled()) {
                            swipeRefreshLayout.setEnabled(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(!swipeRefreshLayout.isEnabled()) {
                            // 用户抬起手指，恢复刷新
                            swipeRefreshLayout.setEnabled(true);
                        }
                        break;
                }
                return false;
            }
        });

        rank_tabLayout.setupWithViewPager(rank_viewPager);

        rank_tabLayout.getTabAt(0).setText("借阅数量");
        rank_tabLayout.getTabAt(1).setText("进馆次数");
        rank_tabLayout.getTabAt(2).setText("预约数量");
    }

    //初始化数据以及预约显示的控制---------------???
    void init_order(){
        //将子页加入ViewPage
        List<Fragment> fragmentList = new ArrayList<>();
        order_item1=new HomePage_Order_Item();
        fragmentList.add(order_item1);
        order_item2=new HomePage_Order_Item();
        fragmentList.add(order_item2);
        order_item3=new HomePage_Order_Item();
        fragmentList.add(order_item3);

        order_viewPager.setAdapter(new MyViewPageAdapter(fragmentList,this.getChildFragmentManager()));
        //避免滑动冲突
        order_viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        // 记录点击到ViewPager时候，手指的X坐标
                        order_mLastX = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 超过阈值，关闭刷新
                        if(Math.abs(motionEvent.getX() - order_mLastX) > 60f&&swipeRefreshLayout.isEnabled()) {
                            swipeRefreshLayout.setEnabled(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if(!swipeRefreshLayout.isEnabled()) {
                            // 用户抬起手指，恢复刷新
                            swipeRefreshLayout.setEnabled(true);
                        }
                        break;
                }
                return false;
            }
        });

        order_tabLayout.setupWithViewPager(order_viewPager);

        order_tabLayout.getTabAt(0).setText("活动预约");
        order_tabLayout.getTabAt(1).setText("研修预约");
        order_tabLayout.getTabAt(2).setText("座位预约");
    }


    public void changeBannerState(boolean state){
        if(banner==null)
            return;
        if(state)
            banner.startAutoPlay();
        else
            banner.stopAutoPlay();
    }

    @Override
    public void onNext(String resulte, String method) {
        if (method.equals(getRankApi.getMethod())) {
            BaseResultEntity<List<Raking>> result = null;
            try {
                result = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<List<Raking>>>() {
                        });
            } catch (Exception e) {
                e.printStackTrace();
                Utils.showToast("解析错误");
                Logger.e(this.getClass(), "解析错误！！！！！！！！！！");
                return;
            }
            if (result != null) {
                if (result.getStatus() == Constant.SUCCESS) {
                    setRank(result.getData());
                } else {
                    Utils.showErrorMsgToast(result.getMessage(), "失败");
                }
            }
        }

        //商榷
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
            Utils.showToast("刷新完成");
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
            Utils.showToast("刷新失败");
        }
    }

    private boolean setRank(List<Raking> tempRakingList){
        if(tempRakingList==null){
            return false;
        }
        rakingList1=new ArrayList<>();
        rakingList2=new ArrayList<>();
        rakingList3=new ArrayList<>();
        rakingList4=new ArrayList<>();
        Raking raking;

        try{
            for(int i=0;i<tempRakingList.size();i++){
                raking=tempRakingList.get(i);
                if(raking.getType().equals(Constant.RANK1))
                    rakingList1.add(raking);
                else if(raking.getType().equals(Constant.RANK2))
                    rakingList2.add(raking);
                else if(raking.getType().equals(Constant.RANK3))
                    rakingList3.add(raking);
                else if(raking.getType().equals(Constant.RANK4))
                    rakingList4.add(raking);
            }
            Value.setRankList(rakingList1,rakingList2,rakingList3,rakingList4);
            if(rank_item1!=null) ((HomePage_Rank_Item_small)rank_item1).refreshData();
            if(rank_item2!=null) ((HomePage_Rank_Item_small)rank_item2).refreshData();
            if(rank_item3!=null) ((HomePage_Rank_Item_small)rank_item3).refreshData();

        }catch (Exception e){
            Logger.e(this.getClass(),"设置排名错误！！！！！！！！！！");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
