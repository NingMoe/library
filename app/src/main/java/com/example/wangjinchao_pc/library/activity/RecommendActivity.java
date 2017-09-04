package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.Constant.Value;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.adapter.BookAdapter;
import com.example.wangjinchao_pc.library.adapter.DividerItemDecoration;
import com.example.wangjinchao_pc.library.adapter.RecyclerViewAdapterWrapper;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.enity.api.GetRecommendBookApi;
import com.example.wangjinchao_pc.library.enity.result.BaseResultEntity;
import com.example.wangjinchao_pc.library.util.Utils;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/10.
 */

public class RecommendActivity extends ToolbarActivity implements HttpOnNextListener{
    public static void start(Context context){
        Intent intent =new Intent(context,RecommendActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.recommend_container)
    RecyclerView recyclerView;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private int page = Value.startPage;
    private List<HashMap<String, Object>> datas = new ArrayList<>();
    private RecyclerViewAdapterWrapper adapter;

    //网络请求接口
    private HttpManager httpManager;
    private GetRecommendBookApi getRecommendBookApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_recommend);
        ButterKnife.bind(this);
        httpManager=new HttpManager(this,this);
        //设置加载项
        setLoadView(frameLayout);
        initActionBar();
        init_recyclerView();
        init_Data();

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

    }
    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("荐购");
    }
    /**
     * 初始化recyclerView
     */
    private void init_recyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        //上拉加载
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(!recyclerView.canScrollVertically(1)){
                    loadData();
                }
            }
        });

        adapter=new RecyclerViewAdapterWrapper(new BookAdapter(this,datas,BookAdapter.RECOMMEND));
        recyclerView.setAdapter(adapter);
    }
    /**
     * 刷新
     */
    private void refresh(){
        getRecommendBookApi=new GetRecommendBookApi(page);
        httpManager.doHttpDeal(getRecommendBookApi);
    }

    void init_Data(){

        startLoading();
        HashMap<String, Object> map = null;
        for(int i=0;i<10;i++){
            map=new HashMap<String, Object>();
            map.put(BookAdapter.TITLE,"最好的我们");
  /*          map.put(BookAdapter.BOOK_IMG,R.drawable.android);*/
            map.put(BookAdapter.AUTHOR,"八月长安");
            map.put(BookAdapter.IS_COLLECT,false);
            map.put(BookAdapter.COLLECT_NUMBER,"123");
            map.put(BookAdapter.CONTENT,"八月长安创作五年全新力作！继《你好，旧时光》《暗恋·橘生淮南》之后第三部长篇小说！ ·随书附赠著名音乐人杨炅翰创作同名主打歌CD，以及记忆明信片，收录知名画手绘制精美彩插，极具收藏价值。 ·人们总是觉得青春从不曾");
            map.put(BookAdapter.PRICE,"28.60");
            map.put(BookAdapter.STYLE,BookAdapter.ELECT);
            datas.add(map);
        }
        stopLoading();
    }

    /**
     * 加载数据
     */
    void loadData(){
        getRecommendBookApi=new GetRecommendBookApi(page);
        httpManager.doHttpDeal(getRecommendBookApi);
    }

    @Override
    public void onNext(String resulte, String method) {
        BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                TypeReference<BaseResultEntity<String>>() {
                });
        Utils.showToast("成功");
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(ApiException e, String method) {

        //测试————————————————————
        HashMap<String, Object> map = null;
        map=new HashMap<String, Object>();
        map.put(BookAdapter.TITLE,"最好的我们");
  /*          map.put(BookAdapter.BOOK_IMG,R.drawable.android);*/
        map.put(BookAdapter.AUTHOR,"八月长安");
        map.put(BookAdapter.IS_COLLECT,false);
        map.put(BookAdapter.COLLECT_NUMBER,"123");
        map.put(BookAdapter.CONTENT,"八月长安创作五年全新力作！继《你好，旧时光》《暗恋·橘生淮南》之后第三部长篇小说！ ·随书附赠著名音乐人杨炅翰创作同名主打歌CD，以及记忆明信片，收录知名画手绘制精美彩插，极具收藏价值。 ·人们总是觉得青春从不曾");
        map.put(BookAdapter.PRICE,"30.60");
        map.put(BookAdapter.STYLE,BookAdapter.PAPER);
        datas.add(map);
        //通知更新
        adapter.notifyDataSetChanged();

        Utils.showToast("失败");
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }
}
