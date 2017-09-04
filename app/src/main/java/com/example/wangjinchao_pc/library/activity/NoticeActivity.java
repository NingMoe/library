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
import com.example.wangjinchao_pc.library.adapter.NoticeAdapter;
import com.example.wangjinchao_pc.library.adapter.RecyclerViewAdapterWrapper;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.enity.api.GetCodeApi;
import com.example.wangjinchao_pc.library.enity.api.GetNoticeApi;
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

public class NoticeActivity  extends ToolbarActivity implements HttpOnNextListener{
    public static void start(Context context){
        Intent intent =new Intent(context,NoticeActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.mine_container)
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
    private GetNoticeApi getNoticeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_notice);
        ButterKnife.bind(this);
        httpManager=new HttpManager(this,this);

        //设置加载项
        setLoadView(frameLayout);

        initActionBar();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        //监听滑动
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(!recyclerView.canScrollVertically(1)){
                    loadData();
                }
            }
        });

        //——————————
        init_Data();

        adapter=new RecyclerViewAdapterWrapper(new NoticeAdapter(this,datas));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNoticeApi=new GetNoticeApi(MyApplication.getToken());
                httpManager.doHttpDeal(getNoticeApi);
            }
        });

    }
    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        setTitle("通知信息");
        setDisplayHomeAsUpEnabled(false);
    }

    //_————————————————
    void init_Data(){
        startLoading();

        HashMap<String, Object> map = null;
        for(int i=0;i<10;i++){
            map=new HashMap<String, Object>();
            map.put(NoticeAdapter.TIME," 2013.09.0"+i);
            map.put(NoticeAdapter.CONTENT,"图书馆将从2017年8月24日下午2:30开始,正式为广大师生读者办理借还手续。请需要借还图书和查询资料的师生,带上本人的借书证。");
            datas.add(map);
        }
        stopLoading();
    }

    void loadData(){
        getNoticeApi=new GetNoticeApi(MyApplication.getToken());
        httpManager.doHttpDeal(getNoticeApi);
    }

    @Override
    public void onNext(String resulte, String method) {
        if(method.equals(getNoticeApi.getMethod())){
            BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<String>>() {
                    });
            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
            Utils.showToast("成功");
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        if(method.equals(getNoticeApi.getMethod())){
            Utils.showToast("失败");
            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
        }
        HashMap<String, Object> map = null;
        for(int i=0;i<10;i++){
            map=new HashMap<String, Object>();
            map.put(NoticeAdapter.TIME," 2017.09.0"+i);
            map.put(NoticeAdapter.CONTENT,"图书馆将从2015年8月24日下午2:30开始,正式为广大师生读者办理借还手续。请需要借还图书和查询资料的师生,带上本人的借书证。");
            datas.add(map);
        }
        adapter.notifyDataSetChanged();
    }
}
