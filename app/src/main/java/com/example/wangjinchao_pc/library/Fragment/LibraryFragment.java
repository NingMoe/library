package com.example.wangjinchao_pc.library.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangjinchao_pc.library.R;
import com.example.wangjinchao_pc.library.View.AutoSearchClearEditText;
import com.example.wangjinchao_pc.library.activity.BookSearchResultActivity;
import com.example.wangjinchao_pc.library.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangjinchao-PC on 2017/7/6.
 */

public class LibraryFragment extends BaseFragment implements AutoSearchClearEditText.OnSearchClickListener{
    View view;
    @BindView(R.id.searchView)
    AutoSearchClearEditText searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_library, null);
        ButterKnife.bind(this, view);

        searchView.setOnSearchClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }


    @Override
    public void onSearchClick(View view) {
        Log.d("Library Search",searchView.getText().toString());

        BookSearchResultActivity.start(view.getContext(),searchView.getText().toString());
    }


}
