package com.wul.oa_article.view.main.home.myorder;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.bean.request.OrderRequest;
import com.wul.oa_article.mvp.MVPBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的订单
 */

public class MyOrderFragment extends MVPBaseFragment<MyOrderContract.View, MyOrderPresenter>
        implements MyOrderContract.View {


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
        getOrderByTask();
    }


    /**
     * 初始化布局
     */
    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText("0\n所有任务"));
        tabLayout.addTab(tabLayout.newTab().setText("0\n我自己的"));
        tabLayout.addTab(tabLayout.newTab().setText("0\n我分派的"));
        tabLayout.addTab(tabLayout.newTab().setText("0\n已完成"));
    }


    /**
     * 获取我的任务
     */
    private void getOrderByTask() {
        OrderRequest request = new OrderRequest();
        request.setPageNum(1);
        request.setPageSize(1000);
        request.setUserId(1);
        HttpServerImpl.getOrderByTask(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String message) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
