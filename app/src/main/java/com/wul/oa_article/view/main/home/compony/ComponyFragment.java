package com.wul.oa_article.view.main.home.compony;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 公司订单
 */
public class ComponyFragment extends MVPBaseFragment<ComponyContract.View, ComponyPresenter>
        implements ComponyContract.View {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_home_list, null);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }


    /**
     * 初始化布局
     */
    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText("0\n全部"));
        tabLayout.addTab(tabLayout.newTab().setText("0\n进行中"));
        tabLayout.addTab(tabLayout.newTab().setText("0\n已完成"));
        tabLayout.addTab(tabLayout.newTab().setText("0\n已取消"));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
