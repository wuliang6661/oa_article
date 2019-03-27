package com.wul.oa_article.view.main.home.compony;


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

import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.ComplanOrderBo;
import com.wul.oa_article.bean.request.OrderRequest;
import com.wul.oa_article.mvp.MVPBaseFragment;
import com.wul.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.wul.oa_article.widget.lgrecycleadapter.LGViewHolder;

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
        setBarStyleByTab(false);
        getOrderByTask(0);
    }


    /**
     * 初始化布局
     */
    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText("0\n全部"));
        tabLayout.addTab(tabLayout.newTab().setText("0\n进行中"));
        tabLayout.addTab(tabLayout.newTab().setText("0\n已完成"));
        tabLayout.addTab(tabLayout.newTab().setText("0\n已取消"));
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
            renwuTime.setText("");
        } else {
            personLayout.setVisibility(View.VISIBLE);
            barOrderMessage.setText("创建人");
            barOrderType.setText("客户简称");
            barPersonTime.setText("任务时限");
            renwuTime.setText("订单交期");
        }
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
        HttpServerImpl.getComplayList(request).subscribe(new HttpResultSubscriber<List<ComplanOrderBo>>() {
            @Override
            public void onSuccess(List<ComplanOrderBo> s) {
                setAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
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
                holder.setText(R.id.order_name, myOrderBO.getCName());
                holder.setText(R.id.order_num, myOrderBO.getCNum());
//                holder.setText(R.id.order_message, myOrderBO.getTaskName());
//                holder.setText(R.id.order_nick_name, myOrderBO.getNickName());
                TextView orderType = (TextView) holder.getView(R.id.order_type);
                orderType.setText(myOrderBO.getClientName());

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
