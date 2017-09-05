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
import com.example.wangjinchao_pc.library.Constant.Constant;
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
import com.example.wangjinchao_pc.library.enity.domain.Arrears;
import com.example.wangjinchao_pc.library.enity.result.BaseResultEntity;
import com.example.wangjinchao_pc.library.util.Logger;
import com.example.wangjinchao_pc.library.util.Utils;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.http.HttpManager;
import com.retrofit_rx.listener.HttpOnNextListener;

import java.text.DecimalFormat;
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

    //对加载动画设置flag
    private boolean loadingFlag=true;

    //是否还可以加载更多数据
    private boolean loadingMoreEnabled=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_detail);
        ButterKnife.bind(this);
        type=getIntent().getIntExtra(TYPE,TYPE_BOOK);

        initActionBar();
        initHttp();

        //设置加载项
        setLoadView(frameLayout);

        debt_container.setLayoutManager(new LinearLayoutManager(this));
        debt_container.addItemDecoration(new DividerItemDecoration(this));

        //上拉加载数据----更改？？？？？？？？？？？？？
        debt_container.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(loadingMoreEnabled&&dy>0&&!recyclerView.canScrollVertically(1)){
                    loadData();
                }
            }
        });

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

        initData();
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
     * 初始化网络相关对象
     */
    private void initHttp(){
        httpManager=new HttpManager(this,this);
        inqueryDuePriceApi=new InqueryDuePriceApi();
        inqueryDueBookApi=new InqueryDueBookApi();
    }
    /**
     * 初始化---请求以及初始数据获取
     */
    private void initData(){
        loadingFlag=true;
        startLoading();
        if(type==TYPE_PRICE){
            httpManager.doHttpDeal(inqueryDuePriceApi);
        }
        else if(type==TYPE_BOOK){
            httpManager.doHttpDeal(inqueryDueBookApi);
        }
    }

/*    //测试————————————————————————
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
    }*/

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

    /**
     * 向datas中添加数据
     */
    boolean addData(List<Arrears> arrearsList){
        DecimalFormat df = null;
        if(arrearsList==null)
            return false;
        try{
            HashMap<String, Object> map = null;
            if(datas.size()<=0){
                map=new HashMap<String, Object>();
                map.put(DebtDetailAdapter.DEBT_SUM,"0");
                datas.add(map);
            }
            double sum=0;
            if(type==TYPE_PRICE){
                df = new DecimalFormat("#.00");
                for(int i=0;i<arrearsList.size();i++){
                    map=new HashMap<String, Object>();
                    map.put(DebtDetailAdapter.TIME,arrearsList.get(i).getOrder_date());
                    map.put(DebtDetailAdapter.TITLE,arrearsList.get(i).getBook_name());
                    map.put(DebtDetailAdapter.DEBT_PRICE,arrearsList.get(i).getFee());
                    datas.add(map);
                    sum+=Double.parseDouble(arrearsList.get(i).getFee());
                }
            }else if(type==TYPE_BOOK){
                df = new DecimalFormat("#");
                for(int i=0;i<arrearsList.size();i++){
                    map=new HashMap<String, Object>();
                    map.put(DebtDetailAdapter.TIME,arrearsList.get(i).getOrder_date());
                    map.put(DebtDetailAdapter.TITLE,arrearsList.get(i).getBook_name());
                    map.put(DebtDetailAdapter.DEBT_PRICE,arrearsList.get(i).getFee());
                    datas.add(map);
                    sum++;
                }
            }
            Logger.d(this.getClass(),"sum: "+sum);
            if(sum>0)
                datas.get(0).put(DebtDetailAdapter.DEBT_SUM,
                        df.format(Double.parseDouble(datas.get(0).get(DebtDetailAdapter.DEBT_SUM).toString()) + sum));
            Logger.d(this.getClass(),"总数: "+datas.get(0).get(DebtDetailAdapter.DEBT_SUM).toString());
        }catch (Exception e){
            Logger.e(this.getClass(),"获取数据错误！！！！！！！！！！");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void onNext(String resulte, String method) {
        //标志是否刷新或者更新成功
        boolean flag=true;

        if (method.equals(inqueryDuePriceApi.getMethod())) {
            BaseResultEntity<List<Arrears>> result=null;
            try{
                result = JSONObject.parseObject(resulte, new TypeReference<BaseResultEntity<List<Arrears>>>() {

                });
                Logger.d(this.getClass(),"接收数:"+result.getData().size());
            }catch (Exception e){
                e.printStackTrace();
                flag=false;
                Utils.showToast("解析错误");
                Logger.e(this.getClass(),"解析错误！！！！！！！！！！");
                return;
            }
            //添加数据
            if(result!=null) {
                if(result.getStatus()== Constant.SUCCESS)
                    if(addData(result.getData())){
                        adapter.notifyDataSetChanged();
                        flag=true;
                    }
                    else
                        flag=false;
                else if(result.getStatus()== Constant.ERROR){
                    loadingMoreEnabled=false;
                    flag=false;
                }
            }
            if(flag)
                Utils.showToast("成功");
            else
                Utils.showToast("失败");

            if(loadingFlag){
                stopLoading();
                loadingFlag=false;
            }

            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
        }else if(method.equals(inqueryDueBookApi.getMethod())){
            BaseResultEntity<List<Arrears>> result=null;
            try{
                result = JSONObject.parseObject(resulte, new TypeReference<BaseResultEntity<List<Arrears>>>() {

                });
                Logger.d(this.getClass(),"接收数:"+result.getData().size());
            }catch (Exception e){
                e.printStackTrace();
                flag=false;
                Utils.showToast("解析错误");
                Logger.e(this.getClass(),"解析错误！！！！！！！！！！");
                return;
            }
            //添加数据
            if(result!=null) {
                if(result.getStatus()== Constant.SUCCESS)
                    if(addData(result.getData())){
                        adapter.notifyDataSetChanged();
                        flag=true;
                    }
                    else
                        flag=false;
                else if(result.getStatus()== Constant.ERROR){
                    loadingMoreEnabled=false;
                    flag=false;
                }
            }
            if(flag)
                Utils.showToast("成功");
            else
                Utils.showToast("失败");

            if(loadingFlag){
                stopLoading();
                loadingFlag=false;
            }
            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(ApiException e, String method) {
        if (method.equals(inqueryDuePriceApi.getMethod())) {
            Utils.showToast(e.getDisplayMessage());

            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
            if(loadingFlag){
                stopLoading();
                loadingFlag=false;
            }
        }else if(method.equals(inqueryDueBookApi.getMethod())){
            Utils.showToast(e.getDisplayMessage());

            if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
            if(loadingFlag){
                stopLoading();
                loadingFlag=false;
            }
        }
    }
}
