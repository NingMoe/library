package com.example.wangjinchao_pc.library.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.wangjinchao_pc.library.Constant.Value;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.activity.BookSearchResultActivity;
import com.example.wangjinchao_pc.library.adapter.BookAdapter;
import com.example.wangjinchao_pc.library.adapter.DividerItemDecoration;
import com.example.wangjinchao_pc.library.adapter.RecyclerViewAdapterWrapper;
import com.example.wangjinchao_pc.library.base.BaseFragment;
import com.example.wangjinchao_pc.library.enity.baseResult.BaseResultEntity;
import com.example.wangjinchao_pc.library.util.Utils;
import com.retrofit_rx.exception.ApiException;
import com.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/9.
 */

public class BookFragment extends BaseFragment implements HttpOnNextListener{
    View view;

    @BindView(R.id.book_container)
    RecyclerView recyclerView;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.sort_img)
    ImageView sort_img;
    @BindView(R.id.book_numbers)
    TextView book_numbers;
    @BindView(R.id.type)
    TextView book_type;

    //类型
    private int type;

    //排序
    private boolean asc_sort;

    private int page = Value.startPage;
    private List<HashMap<String, Object>> datas = new ArrayList<>();
    private RecyclerViewAdapterWrapper adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).from(getContext()).inflate(R.layout.fragment_book, null);
        ButterKnife.bind(this, view);
        //设置加载项
        setLoadView(frameLayout);

        init_Data();
        init_book_type();
        init_sort();
        init_recyclerView();

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    /**
     * 初始化book_type
     */
    private void init_book_type(){
        switch(type){
            case BookSearchResultActivity.TYPE_ABOUT:
                book_type.setText("为您找到相关图书");
                break;
            case BookSearchResultActivity.TYPE_GOOD:
                book_type.setText("为您找到好评图书");
                break;
            case BookSearchResultActivity.TYPE_NEW:
                book_type.setText("为您找到热门图书");
                break;
            case BookSearchResultActivity.TYPE_RED:
                book_type.setText("为您找到新书");
                break;
        }
    }
    /**
     * 初始化sort顺序
     */
    private void init_sort(){
        if(asc_sort){
            sort_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_sort_24dp));
            //??????????????
        }else{
            sort_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_sort_24dp));
            //?????????????
        }
        //监听排序点击
        sort_img.setClickable(true);
        sort_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asc_sort=!asc_sort;
                if(asc_sort){
                    sort_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_sort_24dp));
                    //????????????

                }else{
                    sort_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_sort_24dp));
                    //????????????
                }
            }
        });
    }
    /**
     * 初始化recyclerView
     */
    private void init_recyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));

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

        adapter=new RecyclerViewAdapterWrapper(new BookAdapter(this.getContext(),datas));
        recyclerView.setAdapter(adapter);
    }
    /**
     * 刷新
     */
    private void refresh(){
        switch(type){
            case BookSearchResultActivity.TYPE_ABOUT:
                ((BookSearchResultActivity)getActivity()).getAboutBookApi(page,this);
                break;
            case BookSearchResultActivity.TYPE_GOOD:
                ((BookSearchResultActivity)getActivity()).getGoodBookApi(page,this);
                break;
            case BookSearchResultActivity.TYPE_NEW:
                ((BookSearchResultActivity)getActivity()).getNewBookApi(page,this);
                break;
            case BookSearchResultActivity.TYPE_RED:
                ((BookSearchResultActivity)getActivity()).getRedBookApi(page,this);
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }

    //后期去掉------测试
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
            map.put(BookAdapter.STYLE,BookAdapter.PAPER);
            map.put(BookAdapter.BOOK_IMG,"https://b-ssl.duitang.com/uploads/item/201412/13/20141213120059_WAWSF.jpeg");
            datas.add(map);
        }
        stopLoading();

    }
    /**
     * 加载数据
     */
    void loadData(){
        switch(type){
            case BookSearchResultActivity.TYPE_ABOUT:
                ((BookSearchResultActivity)getActivity()).getAboutBookApi(page,this);
                break;
            case BookSearchResultActivity.TYPE_GOOD:
                ((BookSearchResultActivity)getActivity()).getGoodBookApi(page,this);
                break;
            case BookSearchResultActivity.TYPE_NEW:
                ((BookSearchResultActivity)getActivity()).getNewBookApi(page,this);
                break;
            case BookSearchResultActivity.TYPE_RED:
                ((BookSearchResultActivity)getActivity()).getRedBookApi(page,this);
                break;
        }
    }



    @Override
    public void onNext(String resulte, String method) {
        switch(type){
            case BookSearchResultActivity.TYPE_ABOUT:
                BaseResultEntity<String> about_result = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<String>>() {
                        });
                Utils.showToast("成功");
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                break;
            case BookSearchResultActivity.TYPE_GOOD:
                BaseResultEntity<String> good_result = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<String>>() {
                        });
                Utils.showToast("成功");
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                break;
            case BookSearchResultActivity.TYPE_NEW:
                BaseResultEntity<String> new_result = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<String>>() {
                        });
                Utils.showToast("成功");
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                break;
            case BookSearchResultActivity.TYPE_RED:
                BaseResultEntity<String> red_result = JSONObject.parseObject(resulte, new
                        TypeReference<BaseResultEntity<String>>() {
                        });
                Utils.showToast("成功");
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                break;
        }
    }

    @Override
    public void onError(ApiException e, String method) {

        //加载数据----测试
        HashMap<String, Object> map = null;
        map=new HashMap<String, Object>();
        map.put(BookAdapter.TITLE,"最好的我们");
  /*          map.put(BookAdapter.BOOK_IMG,R.drawable.android);*/
        map.put(BookAdapter.AUTHOR,"八月长安");
        map.put(BookAdapter.IS_COLLECT,false);
        map.put(BookAdapter.COLLECT_NUMBER,"123");
        map.put(BookAdapter.CONTENT,"八月长安创作五年全新力作！继《你好，旧时光》《暗恋·橘生淮南》之后第三部长篇小说！ ·随书附赠著名音乐人杨炅翰创作同名主打歌CD，以及记忆明信片，收录知名画手绘制精美彩插，极具收藏价值。 ·人们总是觉得青春从不曾");
        map.put(BookAdapter.PRICE,"30.60");
        map.put(BookAdapter.STYLE,BookAdapter.ELECT);
        map.put(BookAdapter.BOOK_IMG,"http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        datas.add(map);
        //通知更新
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

        Utils.showToast("失败");
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAsc_sort() {
        return asc_sort;
    }

    public void setAsc_sort(boolean asc_sort) {
        this.asc_sort = asc_sort;
    }
}
