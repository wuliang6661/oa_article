package com.article.oa_article.view.main.home.myorder;


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

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.MyOrderBO;
import com.article.oa_article.bean.TaskNumBO;
import com.article.oa_article.bean.request.OrderRequest;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.view.AcceptedTaskActivity;
import com.article.oa_article.view.MyOrderActivity;
import com.article.oa_article.view.order_details.Order_detailsActivity;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.StringUtils;

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
//        getOrderByTask(all, 0);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        switch (position) {
            case 0:
                getOrderByTask(all, position);
                break;
            case 1:
                getOrderByTask(myziji, position);
                break;
            case 2:
                getOrderByTask(myfenpai, position);
                break;
            case 3:
                getOrderByTask(wancheng, position);
                break;
        }
        mPresenter.getTaskNum(Integer.parseInt(MyApplication.getCommonId()));
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
        request.setCompanyId(MyApplication.getCommonId());
    }


    private TabLayout.Tab tab1, tab2, tab3, tab4;

    /**
     * 初始化布局
     */
    private void initView() {
        tab1 = tabLayout.newTab().setText("--\n已接受");
        tab2 = tabLayout.newTab().setText("--\n自己做");
        tab3 = tabLayout.newTab().setText("--\n派人做");
        tab4 = tabLayout.newTab().setText("--\n已完成");
        tabLayout.addTab(tab1);
        tabLayout.addTab(tab2);
        tabLayout.addTab(tab3);
        tabLayout.addTab(tab4);
        tabLayout.getTabAt(0).select();
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
                        tab1.setText(s.size() + "\n已接受");
                        break;
                    case 1:
                        tab2.setText(s.size() + "\n自己做");
                        break;
                    case 2:
                        tab3.setText(s.size() + "\n派人做");
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

    LGRecycleViewAdapter<MyOrderBO> adapter;

    List<MyOrderBO> orders;

    /**
     * 设置适配器
     */
    private void setAdapter(List<MyOrderBO> s) {
        this.orders = s;
        if (adapter != null) {
            adapter.setData(s);
            return;
        }
        adapter = new LGRecycleViewAdapter<MyOrderBO>(s) {
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
                TextView taskTime = (TextView) holder.getView(R.id.task_time);
                taskTime.setText(myOrderBO.getPlanCompleteDate().replaceAll("-", "/"));
                taskTime.setTextColor(Color.parseColor("#8D8C91"));
                orderType.setTextColor(Color.parseColor("#8D8C91"));
                holder.getView(R.id.cancle_img).setVisibility(View.GONE);
                switch (myOrderBO.getStatus()) {
                    case 0:
                        holder.setText(R.id.order_type, "未接受");
                        orderType.setTextColor(Color.parseColor("#F4CA40"));
                        break;
                    case 1:
                        holder.setText(R.id.order_type, "进行中");
                        break;
                    case 2:
                        holder.setText(R.id.order_type, "已完成");
//                        holder.setText(R.id.order_nick_name, "--");
//                        holder.setText(R.id.task_time, "--");
                        holder.getView(R.id.cancle_img).setVisibility(View.VISIBLE);
                        holder.setImageResurce(R.id.cancle_img, R.drawable.order_suress_img);
                        break;
                    case 3:
                        holder.setText(R.id.order_type, "已取消");
//                        holder.setText(R.id.order_nick_name, "--");
//                        holder.setText(R.id.task_time, "--");
                        holder.getView(R.id.cancle_img).setVisibility(View.VISIBLE);
                        holder.setImageResurce(R.id.cancle_img, R.drawable.yi_cancle);
                        break;
                    case 4:
                        holder.setText(R.id.order_type, "未分派");
                        orderType.setTextColor(Color.parseColor("#F4CA40"));
                        break;
                    default:
                        holder.setText(R.id.order_type, "已删除");
                        break;
                }
                TextView surplus_time = (TextView) holder.getView(R.id.surplus_time);
                if (myOrderBO.getStatus() == 3) {
                    surplus_time.setText("--");
                    surplus_time.setTextColor(Color.parseColor("#8D8C91"));
                    surplus_time.setTextSize(11);
                } else if (myOrderBO.getStatus() == 2) {
                    surplus_time.setText(myOrderBO.getActualCompleteDate().replaceAll("-", "/"));
                    surplus_time.setTextColor(Color.parseColor("#8D8C91"));
                    surplus_time.setTextSize(11);
                    if (myOrderBO.getIsOverdue() == 1) {
                        surplus_time.setTextColor(Color.parseColor("#E92B2B"));
                        taskTime.setTextColor(Color.parseColor("#E92B2B"));
                    } else {
                        surplus_time.setTextColor(Color.parseColor("#8D8C91"));
                        taskTime.setTextColor(Color.parseColor("#8D8C91"));
                    }
                } else {
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
                            taskTime.setTextColor(Color.parseColor("#E92B2B"));
                        }
                    }
                }
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, (view, i) -> {
            MyOrderBO orderBO = orders.get(i);
            switch (orderBO.getPage()) {
                case 1:   //待接受
                    Bundle bundle = new Bundle();
                    bundle.putInt("taskId", orderBO.getId());
                    bundle.putBoolean("isHome", true);
                    gotoActivity(AcceptedTaskActivity.class, bundle, false);
                    break;
                case 2:   //  我的任务
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("taskId", orderBO.getId());
                    gotoActivity(MyOrderActivity.class, bundle1, false);
                    break;
                case 3:   // 任务详情
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("id", orderBO.getId());
                    bundle2.putBoolean("isOrder", false);
                    gotoActivity(Order_detailsActivity.class, bundle2, false);
                    break;
                case 4:  //订单详情
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("id", orderBO.getId());
                    bundle3.putBoolean("isOrder", true);
                    gotoActivity(Order_detailsActivity.class, bundle3, false);
                    break;
            }
//            if (orderBO.getStatus() == 0) {
//                return;
//            }
//            if (orderBO.getStatus() == 4) {   //未分派状态
//                Bundle bundle = new Bundle();
//                bundle.putInt("taskId", orderBO.getId());
//                bundle.putBoolean("isHome", true);
//                bundle.putBoolean("isNoPai", true);
//                gotoActivity(AcceptedTaskActivity.class, bundle, false);
//                return;
//            }
//            if (orderBO.getIsMe() == 0) {   //分派给我的
//                if (orderBO.getStatus() == 0) {  //待接受
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("taskId", orderBO.getId());
//                    bundle.putBoolean("isHome", true);
//                    gotoActivity(AcceptedTaskActivity.class, bundle, false);
//                } else if (orderBO.getStatus() == 1) {  //进行中
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("taskId", orderBO.getId());
//                    gotoActivity(MyOrderActivity.class, bundle, false);
//                } else {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("taskId", orderBO.getId());
//                    gotoActivity(MyOrderActivity.class, bundle, false);
//                }
//            } else if (orderBO.getIsMe() == 1) {   //我分派的
//                if (orderBO.getStatus() != 0) {  //不是待接受
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("id", orderBO.getId());
//                    bundle.putBoolean("isOrder", false);
//                    gotoActivity(Order_detailsActivity.class, bundle, false);
//                } else {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("taskId", orderBO.getId());
//                    bundle.putBoolean("isHome", true);
//                    gotoActivity(AcceptedTaskActivity.class, bundle, false);
//                }
//            } else {   //已完成的
//                Bundle bundle = new Bundle();
//                bundle.putInt("taskId", orderBO.getId());
//                gotoActivity(MyOrderActivity.class, bundle, false);
//            }
        });
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    private TaskNumBO taskNumBO;

    @Override
    public void getTaskNum(TaskNumBO taskNumBO) {
        this.taskNumBO = taskNumBO;
        tab1.setText(taskNumBO.getAll() + "\n已接受");
        tab2.setText(taskNumBO.getMy() + "\n自己做");
        tab3.setText(taskNumBO.getICreate() + "\n派人做");
        tab4.setText(taskNumBO.getComplete() + "\n已完成");
    }
}
