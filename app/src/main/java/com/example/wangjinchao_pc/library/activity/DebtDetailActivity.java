package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.Constant.Value;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.adapter.BookDetailAdapter;
import com.example.wangjinchao_pc.library.adapter.DebtDetailAdapter;
import com.example.wangjinchao_pc.library.adapter.DividerItemDecoration;
import com.example.wangjinchao_pc.library.adapter.RecyclerViewAdapterWrapper;
import com.example.wangjinchao_pc.library.application.MyApplication;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;
import com.example.wangjinchao_pc.library.enity.api.AdvertisementApi;
import com.example.wangjinchao_pc.library.enity.api.GetDueBookNumberApi;
import com.example.wangjinchao_pc.library.enity.api.InqueryDueBookApi;
import com.example.wangjinchao_pc.library.enity.api.InqueryDuePriceApi;
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

public class DebtDetailActivity extends ToolbarActivity implements HttpOnNextListener{
    private final static String TYPE="type";
    public final static int TYPE_BOOK=1;
    public final static int TYPE_PRICE=2;

    public static void start(Context context,int type){
        Intent intent =new Intent(context,DebtDetailActivity.class);
        intent.putExtra(TYPE,type);
        context.startActivity(intent);
    }

    @BindView(R.id.debt_container)
    RecyclerView debt_container;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    //查询类型TYPE_BOOK/TYPE_PRICE
    private int type;

    private int page = Value.startPage;
    private List<HashMap<String, Object>> datas = new ArrayList<>();
    private RecyclerViewAdapterWrapper adapter;
    //网络请求接口
    private HttpManager httpManager;
    private InqueryDuePriceApi inqueryDuePriceApi;
    private InqueryDueBookApi inqueryDueBookApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_detail);
        ButterKnife.bind(this);
        type=getIntent().getIntExtra(TYPE,TYPE_BOOK);

        initActionBar();
        init();

        //设置加载项
        setLoadView(frameLayout);

        debt_container.setLayoutManager(new LinearLayoutManager(this));
        debt_container.addItemDecoration(new DividerItemDecoration(this));

        //上拉加载数据
        debt_container.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(!recyclerView.canScrollVertically(1)){
                    loadData();
                }
            }
        });

        //————————————————————
        //init_Data();

        adapter=new RecyclerViewAdapterWrapper(new DebtDetailAdapter(this,datas,type));
        debt_container.setAdapter(adapter);

        //下拉刷新数据    要改？？？？？？？？？？？？？？？？？？？？？
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                loadData();
            }
        });

    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        if(type==TYPE_PRICE)
            setTitle("欠款");
        else if(type==TYPE_BOOK)
            setTitle("未还书籍");
        setDisplayHomeAsUpEnabled(true);
    }
    /**
     * 初始化---请求以及初始数据获取
     */
    private void init(){
        httpManager=new HttpManager(this,this);
        if(type==TYPE_PRICE){
            inqueryDuePriceApi=new InqueryDuePriceApi();
            httpManager.doHttpDeal(inqueryDuePriceApi);
        }
        else if(type==TYPE_BOOK){
            inqueryDueBookApi=new InqueryDueBookApi();
            httpManager.doHttpDeal(inqueryDueBookApi);
        }
    }

    //测试————————————————————————
    void init_Data(){
        inqueryDuePriceApi=new InqueryDuePriceApi(MyApplication.getToken());
        httpManager.doHttpDeal(inqueryDuePriceApi);

        startLoading();
        HashMap<String, Object> map = null;
        if(type==TYPE_PRICE){
            map=new HashMap<String, Object>();
            map.put(DebtDetailAdapter.DEBT_SUM,"10.02");
            datas.add(map);

            for(int i=0;i<10;i++){
                map=new HashMap<String, Object>();
                map.put(DebtDetailAdapter.TIME,"2017.01.08");
                map.put(DebtDetailAdapter.TITLE,"三重门");
                map.put(DebtDetailAdapter.DEBT_PRICE,"4.00");
                datas.add(map);
            }
        }else{
            map=new HashMap<String, Object>();
            map.put(DebtDetailAdapter.DEBT_SUM,"10");
            datas.add(map);

            for(int i=0;i<10;i++){
                map=new HashMap<String, Object>();
                map.put(DebtDetailAdapter.TIME,"2017.01.08");
                map.put(DebtDetailAdapter.TITLE,"三重门");
                map.put(DebtDetailAdapter.DEBT_PRICE,"3");
                datas.add(map);
            }
        }
        stopLoading();
    }

    /**
     * 加载数据
     */
    void loadData(){
        if(type==TYPE_PRICE){
            inqueryDuePriceApi=new InqueryDuePriceApi(MyApplication.getToken());
            httpManager.doHttpDeal(inqueryDuePriceApi);
        }else if(type==TYPE_BOOK){
            inqueryDueBookApi=new InqueryDueBookApi(MyApplication.getToken());
            httpManager.doHttpDeal(inqueryDueBookApi);
        }
    }


    @Override
    public void onNext(String resulte, String method) {
        if (method.equals(inqueryDuePriceApi.getMethod())) {
            BaseResultEntity<String> result = JSONObject.parseObject(resulte, new
                    TypeReference<BaseResultEntity<String>>() {
                    });
            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
            Utils.showToast("成功");
        }else if(method.equals(inqueryDueBookApi.getMethod())){
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
        if (method.equals(inqueryDuePriceApi.getMethod())) {
            Utils.showToast(e.getDisplayMessage());
            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
        }else if(method.equals(inqueryDueBookApi.getMethod())){
            Utils.showToast(e.getDisplayMessage());
            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
        }
    }
}
