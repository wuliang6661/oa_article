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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    private int position = 0; //当前选中状态，默认全部

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_my_order, null);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    OrderRequest all = new OrderRequest();
    OrderRequest myziji = new OrderRequest();
    OrderRequest myfenpai = new OrderRequest();
    OrderRequest wancheng = new OrderRequest();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventBus.getDefault().register(this);
        initView();

        initBean(all);
        initBean(myziji);
        initBean(myfenpai);
        initBean(wancheng);
        getOrderByTask(all, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initBean(OrderRequest request) {
        request.setPageNum(1);
        request.setPageSize(1000);
        request.setUserId(MyApplication.userBo.getId() + "");
        String commonId;
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            commonId = "0";
        } else {
            commonId = MyApplication.userBo.getCompanys().get(0).getId() + "";
        }
        request.setCompanyId(commonId);
    }


    private TabLayout.Tab tab1, tab2, tab3, tab4;

    /**
     * 初始化布局
     */
    private void initView() {
        tab1 = tabLayout.newTab().setText("--\n所有任务");
        tab2 = tabLayout.newTab().setText("--\n我自己的");
        tab3 = tabLayout.newTab().setText("--\n我分派的");
        tab4 = tabLayout.newTab().setText("--\n已完成");
        tabLayout.addTab(tab1);
        tabLayout.addTab(tab2);
        tabLayout.addTab(tab3);
        tabLayout.addTab(tab4);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                switch (tab.getPosition()) {
                    case 0:
                        getOrderByTask(all, tab.getPosition());
                        break;
                    case 1:
                        getOrderByTask(myziji, tab.getPosition());
                        break;
                    case 2:
                        getOrderByTask(myfenpai, tab.getPosition());
                        break;
                    case 3:
                        getOrderByTask(wancheng, tab.getPosition());
                        break;
                }
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
     * 获取当前选中项
     */
    public int getPosition() {
        return position;
    }


    /**
     * 获取我的任务
     */
    private void getOrderByTask(OrderRequest request, int position) {
        showProgress();
        request.setType(position + "");
        HttpServerImpl.getOrderByTask(request).subscribe(new HttpResultSubscriber<List<MyOrderBO>>() {
            @Override
            public void onSuccess(List<MyOrderBO> s) {
                stopProgress();
                switch (position) {
                    case 0:
                        tab1.setText(s.size() + "\n所有任务");
                        break;
                    case 1:
                        tab2.setText(s.size() + "\n我自己的");
                        break;
                    case 2:
                        tab3.setText(s.size() + "\n我分派的");
                        break;
                    case 3:
                        tab4.setText(s.size() + "\n已完成");
                        break;
                }
                setAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                stopProgress();
                showToast(message);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrderRequest request) {
        switch (request.menuType) {
            case "0":
                all = request;
                getOrderByTask(all, 0);
                break;
            case "1":
                myziji = request;
                getOrderByTask(myziji, 1);
                break;
            case "2":
                myfenpai = request;
                getOrderByTask(myfenpai, 2);
                break;
            case "3":
                wancheng = request;
                getOrderByTask(wancheng, 3);
                break;
        }
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
