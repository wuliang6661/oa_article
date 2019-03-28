package com.wul.oa_article.view.main.home.myorder;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.MyOrderBO;
import com.wul.oa_article.bean.request.OrderRequest;
import com.wul.oa_article.mvp.MVPBaseFragment;
import com.wul.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.wul.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

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
    @BindView(R.id.shengyu_time)
    TextView shengyuTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_my_order, null);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        getOrderByTask(0);
    }


    /**
     * 初始化布局
     */
    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText("0\n所有任务"));
        tabLayout.addTab(tabLayout.newTab().setText("0\n我自己的"));
        tabLayout.addTab(tabLayout.newTab().setText("0\n我分派的"));
        tabLayout.addTab(tabLayout.newTab().setText("0\n已完成"));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getOrderByTask(tab.getPosition());
                if (tab.getPosition() == 3) {
                    shengyuTime.setText("完成时间");
                } else {
                    shengyuTime.setText("剩余时间");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    /**
     * 获取我的任务
     */
    private void getOrderByTask(int position) {
        OrderRequest request = new OrderRequest();
        request.setPageNum(1);
        request.setPageSize(1000);
        request.setUserId(MyApplication.userBo.getId() + "");
        request.setType(position + "");
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            showToast("当前用户没有公司！");
            return;
        }
        request.setCompanyId(MyApplication.userBo.getCompanys().get(0).getId() + "");
        showProgress();
        HttpServerImpl.getOrderByTask(request).subscribe(new HttpResultSubscriber<List<MyOrderBO>>() {
            @Override
            public void onSuccess(List<MyOrderBO> s) {
                stopProgress();
                setAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    /**
     * 设置适配器
     */
    private void setAdapter(List<MyOrderBO> s) {
        LGRecycleViewAdapter<MyOrderBO> adapter = new LGRecycleViewAdapter<MyOrderBO>(s) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_myorder;
            }

            @Override
            public void convert(LGViewHolder holder, MyOrderBO myOrderBO, int position) {
                holder.setText(R.id.order_name, myOrderBO.getCompanyOrderName());
                holder.setText(R.id.order_num, myOrderBO.getCompanyOrderNum());
                holder.setText(R.id.order_message, myOrderBO.getTaskName());
                holder.setText(R.id.order_nick_name, myOrderBO.getNickName());
                TextView orderType = (TextView) holder.getView(R.id.order_type);
                holder.setText(R.id.task_time, myOrderBO.getPlanCompleteDate().replaceAll("-", "/"));
                orderType.setTextColor(Color.parseColor("#8D8C91"));
                switch (myOrderBO.getStatus()) {
                    case 0:
                        holder.setText(R.id.order_type, "待接受");
                        orderType.setTextColor(Color.parseColor("#F4CA40"));
                        break;
                    case 1:
                        holder.setText(R.id.order_type, "进行中");
                        break;
                    case 2:
                        holder.setText(R.id.order_type, "已完成");
                        break;
                    case 3:
                        holder.setText(R.id.order_type, "已取消");
                        break;
                }
                TextView surplus_time = (TextView) holder.getView(R.id.surplus_time);
                if (StringUtils.isEmpty(myOrderBO.getRemainingDate())) {
                    surplus_time.setText(myOrderBO.getPlanCompleteDate().replaceAll("-", "/"));
                    surplus_time.setTextColor(Color.parseColor("#8D8C91"));
                    surplus_time.setTextSize(11);
                } else {
                    surplus_time.setText(myOrderBO.getRemainingDate() + "天");
                    surplus_time.setTextSize(16);
                    if (Integer.parseInt(myOrderBO.getRemainingDate()) > 0) {
                        surplus_time.setTextColor(Color.parseColor("#71EA45"));
                    } else {
                        surplus_time.setTextColor(Color.parseColor("#E92B2B"));
                    }
                }
            }
        };
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
