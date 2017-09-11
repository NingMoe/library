package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.example.wangjinchao_pc.library.Constant.Value;
import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.adapter.BookAdapter;
import com.example.wangjinchao_pc.library.adapter.BookDetailAdapter;
import com.example.wangjinchao_pc.library.adapter.BookDetailDividerItemDecoration;
import com.example.wangjinchao_pc.library.adapter.DividerItemDecoration;
import com.example.wangjinchao_pc.library.adapter.RecyclerViewAdapterWrapper;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/9.
 */

public class BookDetailActivity extends ToolbarActivity implements View.OnClickListener{

    public static void start(Context context, int style,int which){
        Intent intent =new Intent(context,BookDetailActivity.class);

        intent.putExtra(BookAdapter.STYLE,style);
        intent.putExtra(BookAdapter.WHICH,which);

        context.startActivity(intent);
    }

    @BindView(R.id.comment_container) RecyclerView comment_container;
    @BindView(R.id.frameLayout) FrameLayout frameLayout;

    private int page = Value.startPage;
    private List<HashMap<String, Object>> datas = new ArrayList<>();
    private RecyclerViewAdapterWrapper adapter;

    //区分书籍类型
    private int style;
    //判断是否为荐购详情
    private int which;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);

        style=getIntent().getIntExtra(BookAdapter.STYLE,BookAdapter.PAPER);
        which=getIntent().getIntExtra(BookAdapter.WHICH,BookAdapter.COMMON);

        initActionBar();
        //设置加载项
        setLoadView(frameLayout);

        comment_container.setLayoutManager(new LinearLayoutManager(this));
        comment_container.addItemDecoration(new BookDetailDividerItemDecoration(this));

        comment_container.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(!recyclerView.canScrollVertically(1)){
                    loadData();
                }
            }
        });

        init_Data();

        adapter=new RecyclerViewAdapterWrapper(new BookDetailAdapter(this,datas,style,which));
        comment_container.setAdapter(adapter);

    }

    /**
     * 初始化导航栏
     */
    private void initActionBar(){
        if(style==BookAdapter.PAPER)
            setTitle("夏目友人帐"+"(纸质书)");
        else{
            setTitle("夏目友人帐"+"(电子书)");
        }
        setDisplayHomeAsUpEnabled(true);
    }

    //测试
    void init_Data(){
        startLoading();
        HashMap<String, Object> map = null;
        for(int i=0;i<10;i++){
            map=new HashMap<String, Object>();
            map.put(BookDetailAdapter.PERSOM_PATH,"");
            map.put(BookDetailAdapter.NAME,"信心");
            map.put(BookDetailAdapter.CONTENT,"----看书，需要有选择性、时间性。虽说饶雪漫的疼痛、安妮宝贝的细腻、郭敬明的华美都很动人，《沙漏》的扣人心弦，《泡沫之夏》的感人肺腑都令人神往，可毕竟，这些也都只是课外读物，应该是在学习之余阅读的，不能因此而耽误学习、荒废学业。");
            map.put(BookDetailAdapter.SCORE,"8");
            map.put(BookDetailAdapter.COMMENT_RANK,3);
            map.put(BookDetailAdapter.TIME,"2017.07.14");
            map.put(BookDetailAdapter.PERSOM_PATH,"https://img.alicdn.com/bao/uploaded/i4/TB1gN0zNVXXXXclaXXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
            datas.add(map);
        }
        stopLoading();
    }


    void loadData(){



    }


    @Override
    public void onClick(View view) {

    }
}
