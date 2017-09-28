package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.wangjinchao_pc.library.Fragment.BookFragment;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.adapter.MyViewPageAdapter;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.api.GetAboutBookApi;
import com.example.wangjinchao_pc.library.api.GetGoodBookApi;
import com.example.wangjinchao_pc.library.api.GetNewBookApi;
import com.example.wangjinchao_pc.library.api.GetRedBookApi;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/9.
 */

public class BookSearchResultActivity extends ToolbarActivity{
    private final static String SEARCH_CONTENT="search_content";
    public final static int TYPE_ABOUT=1;
    public final static int TYPE_GOOD=2;
    public final static int TYPE_RED=3;
    public final static int TYPE_NEW=4;
    public static void start(Context context,String content){
        Intent intent =new Intent(context,BookSearchResultActivity.class);
        intent.putExtra(SEARCH_CONTENT,content);
        context.startActivity(intent);
    }

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    //4大Fragment页面
    private Fragment about,red,good,new_book;

    //网络请求接口
    private HttpManager httpManager;
    private GetAboutBookApi getAboutBookApi;
    private GetGoodBookApi getGoodBookApi;
    private GetRedBookApi getRedBookApi;
    private GetNewBookApi getNewBookApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        initActionBar();

        initViewPager();
    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("搜索结果");
        /*setDisplayHomeAsUpEnabled(false);*/
    }
    /**
     * 初始化导viewpager
     */
    private void initViewPager(){

        List<Fragment> fragmentList = new ArrayList<>();
        about=new BookFragment();
        ((BookFragment)about).setType(TYPE_ABOUT);
        fragmentList.add(about);
        good=new BookFragment();
        ((BookFragment)good).setType(TYPE_GOOD);
        fragmentList.add(good);
        red=new BookFragment();
        ((BookFragment)red).setType(TYPE_RED);
        fragmentList.add(red);
        new_book=new BookFragment();
        ((BookFragment)new_book).setType(TYPE_NEW);
        fragmentList.add(new_book);

        viewPager.setAdapter(new MyViewPageAdapter(fragmentList, getSupportFragmentManager()));
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

        tabLayout.getTabAt(0).setText("相关");
        tabLayout.getTabAt(1).setText("好评");
        tabLayout.getTabAt(2).setText("热门");
        tabLayout.getTabAt(3).setText("新书");

    }

    public void getAboutBookApi(int page,HttpOnNextListener httpOnNextListener){
        httpManager=new HttpManager(httpOnNextListener,this);
        getAboutBookApi=new GetAboutBookApi(page);
        httpManager.doHttpDeal(getAboutBookApi);
    }

    public void getGoodBookApi(int page,HttpOnNextListener httpOnNextListener){
        httpManager=new HttpManager(httpOnNextListener,this);
        getGoodBookApi=new GetGoodBookApi(page);
        httpManager.doHttpDeal(getGoodBookApi);
    }

    public void getRedBookApi(int page,HttpOnNextListener httpOnNextListener){
        httpManager=new HttpManager(httpOnNextListener,this);
        getRedBookApi=new GetRedBookApi(page);
        httpManager.doHttpDeal(getRedBookApi);
    }

    public void getNewBookApi(int page,HttpOnNextListener httpOnNextListener){
        httpManager=new HttpManager(httpOnNextListener,this);
        getNewBookApi=new GetNewBookApi(page);
        httpManager.doHttpDeal(getNewBookApi);
    }
}
