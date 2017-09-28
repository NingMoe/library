package com.example.wangjinchao_pc.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.base.ToolbarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/7.
 */

public class SearchActivity extends ToolbarActivity implements SearchView.OnQueryTextListener{

    public static void start(Context context){
        Intent intent=new Intent(context,SearchActivity.class);
        context.startActivity(intent);
    }
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.search_listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        ButterKnife.bind(this);
        searchView=(SearchView)findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(true);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(this);
    }

    //文本改变时触发
    @Override
    public boolean onQueryTextChange(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }
}
