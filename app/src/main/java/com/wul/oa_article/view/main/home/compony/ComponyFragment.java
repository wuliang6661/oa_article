package com.wul.oa_article.view.main.home.compony;


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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.ComplanOrderBo;
import com.wul.oa_article.bean.request.ComplayRequest;
import com.wul.oa_article.mvp.MVPBaseFragment;
import com.wul.oa_article.view.CreateTaskActivity;
import com.wul.oa_article.view.OrderDetailsActivity;
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
 * 公司订单
 */
public class ComponyFragment extends MVPBaseFragment<ComponyContract.View, ComponyPresenter>
        implements ComponyContract.View {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    Unbinder unbinder;
    @BindView(R.id.shengyu_time)
    TextView shengyuTime;
    @BindView(R.id.bar_order_name)
    TextView barOrderName;
    @BindView(R.id.bar_order_num)
    TextView barOrderNum;
    @BindView(R.id.bar_order_message)
    TextView barOrderMessage;
    @BindView(R.id.bar_order_type)
    TextView barOrderType;
    @BindView(R.id.bar_order_person)
    TextView barOrderPerson;
    @BindView(R.id.bar_person_time)
    TextView barPersonTime;
    @BindView(R.id.person_layout)
    LinearLayout personLayout;
    @BindView(R.id.renwu_time)
    TextView renwuTime;

    ComplayRequest request;
    private int selePosition;

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

        EventBus.getDefault().register(this);
        initView();
        setBarStyleByTab(false);
        request = new ComplayRequest();
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
        getOrderByTask(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private TabLayout.Tab tab1, tab2, tab3, tab4;

    /**
     * 初始化布局
     */
    private void initView() {
        tab1 = tabLayout.newTab().setText("--\n全部");
        tab2 = tabLayout.newTab().setText("--\n进行中");
        tab3 = tabLayout.newTab().setText("--\n已完成");
        tab4 = tabLayout.newTab().setText("--\n已取消");
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
                selePosition = tab.getPosition();
                getOrderByTask(tab.getPosition());
                if (tab.getPosition() == 3) {
                    setBarStyleByTab(true);
                } else {
                    setBarStyleByTab(false);
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
     * 对导航bar设置文字及显示
     */
    private void setBarStyleByTab(boolean isTabOut) {
        if (isTabOut) {
            personLayout.setVisibility(View.GONE);
            shengyuTime.setText("订单交期");
            renwuTime.setText("");
        } else {
            personLayout.setVisibility(View.VISIBLE);
            barOrderMessage.setText("创建人");
            barOrderType.setText("客户简称");
            barPersonTime.setText("任务时限");
            renwuTime.setText("订单交期");
            shengyuTime.setText("剩余时间");
        }
    }


    /**
     * 获取公司订单
     */
    private void getOrderByTask(int position) {
        request.setType(position + "");
        HttpServerImpl.getComplayList(request).subscribe(new HttpResultSubscriber<List<ComplanOrderBo>>() {
            @Override
            public void onSuccess(List<ComplanOrderBo> s) {
                if (position == 3) {
                    setCancleAdapter(s);
                } else {
                    setAdapter(s);
                }
                switch (position) {
                    case 0:
                        tab1.setText(s.size() + "\n全部");
                        break;
                    case 1:
                        tab2.setText(s.size() + "\n进行中");
                        break;
                    case 2:
                        tab3.setText(s.size() + "\n已完成");
                        break;
                    case 3:
                        tab4.setText(s.size() + "\n已取消");
                        break;
                }
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(ComplayRequest request) {
        this.request = request;
        getOrderByTask(selePosition);
    }


    /**
     * 设置适配器
     */
    private void setAdapter(List<ComplanOrderBo> s) {
        LGRecycleViewAdapter<ComplanOrderBo> adapter = new LGRecycleViewAdapter<ComplanOrderBo>(s) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_complan_order;
            }

            @Override
            public void convert(LGViewHolder holder, ComplanOrderBo myOrderBO, int position) {
                holder.setText(R.id.order_name, myOrderBO.getOrderName());
                holder.setText(R.id.order_num, myOrderBO.getOrderNum());
                holder.setText(R.id.order_message, myOrderBO.getCreateName());
                holder.setText(R.id.order_type, myOrderBO.getClientName());
                holder.setText(R.id.order_nick_name, StringUtils.isEmpty(myOrderBO.getUserName()) ? "--" : myOrderBO.getUserName());
                if (StringUtils.isEmpty(myOrderBO.getTaskPlanDate())) {
                    holder.setText(R.id.nike_name_time, "--");
                } else {
                    holder.setText(R.id.nike_name_time, myOrderBO.getTaskPlanDate().replaceAll("-", "/"));
                }
                TextView task_date = (TextView) holder.getView(R.id.task_date);
                if (myOrderBO.getTaskDate() == 0) {
                    task_date.setText("");
                } else if (myOrderBO.getTaskDate() > 0) {
                    task_date.setText(myOrderBO.getTaskDate() + "天");
                    task_date.setTextColor(Color.parseColor("#71EA45"));
                } else {
                    task_date.setText(myOrderBO.getTaskDate() + "天");
                    task_date.setTextColor(Color.parseColor("#E92B2B"));
                }
                holder.setText(R.id.task_time, myOrderBO.getOrderPlanDate());
                TextView surplus_time = (TextView) holder.getView(R.id.surplus_time);
                if (StringUtils.isEmpty(myOrderBO.getOrderDate())) {
                    surplus_time.setText(myOrderBO.getOrderEndDate().replaceAll("-", "/"));
                    surplus_time.setTextColor(Color.parseColor("#8D8C91"));
                    surplus_time.setTextSize(11);
                } else {
                    surplus_time.setText(myOrderBO.getOrderDate() + "天");
                    surplus_time.setTextSize(16);
                    if (Integer.parseInt(myOrderBO.getOrderDate()) > 0) {
                        surplus_time.setTextColor(Color.parseColor("#71EA45"));
                    } else {
                        surplus_time.setTextColor(Color.parseColor("#E92B2B"));
                    }
                }
                if (myOrderBO.getStatus() == 3) {
                    surplus_time.setText("");
                    holder.getView(R.id.cancle_img).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.cancle_img).setVisibility(View.GONE);
                }
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", s.get(position).getOrderId());
            if (s.get(position).getCreateId().equals(MyApplication.userBo.getId())
                    && s.get(position).getStatus() == 1) {  //如果是当前用户创建,且进行中
                gotoActivity(CreateTaskActivity.class, bundle, false);
            } else {
                gotoActivity(OrderDetailsActivity.class, bundle, false);
            }
        });
        recycleView.setAdapter(adapter);
    }


    /**
     * 设置已取消的适配器
     */
    private void setCancleAdapter(List<ComplanOrderBo> s) {
        LGRecycleViewAdapter<ComplanOrderBo> adapter = new LGRecycleViewAdapter<ComplanOrderBo>(s) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_order_cancle;
            }

            @Override
            public void convert(LGViewHolder holder, ComplanOrderBo complanOrderBo, int position) {
                holder.setText(R.id.order_name, complanOrderBo.getOrderName());
                holder.setText(R.id.order_num, complanOrderBo.getOrderNum());
                holder.setText(R.id.create_name, complanOrderBo.getCreateName());
                holder.setText(R.id.create_nick_name, complanOrderBo.getClientName());
                holder.setText(R.id.order_time, complanOrderBo.getOrderPlanDate());
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", s.get(position).getOrderId());
            gotoActivity(OrderDetailsActivity.class, bundle, false);
        });
        recycleView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
