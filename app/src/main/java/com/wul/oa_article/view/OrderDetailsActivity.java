package com.wul.oa_article.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.bean.OrderAndTaskInfoBO;
import com.wul.oa_article.bean.request.IdRequest;
import com.wul.oa_article.module.order_details.Order_detailsFragment;
import com.wul.oa_article.module.task_allot.Task_allotFragment;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OrderDetailsActivity extends BaseActivity {


    @BindView(R.id.shangji_task_check)
    CheckBox shangjiTaskCheck;
    @BindView(R.id.task_right_button)
    TextView taskRightButton;
    @BindView(R.id.shangji_task_bar)
    LinearLayout shangjiTaskBar;
    @BindView(R.id.task_name)
    TextView taskName;
    @BindView(R.id.task_date)
    TextView taskDate;
    @BindView(R.id.task_person_name)
    TextView taskPersonName;
    @BindView(R.id.shangji_layout)
    LinearLayout shangjiLayout;

    boolean isCreate = false;   //默认当前订单不是当前用户创建
    int id;   //订单或任务id

    Order_detailsFragment orderFragment;
    Task_allotFragment taskFragment;


    @Override
    protected int getLayout() {
        return R.layout.act_order_details;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitleText("订单详情");
        id = getIntent().getExtras().getInt("id");

        orderFragment = Order_detailsFragment.newInstance(1, id);
        taskFragment = Task_allotFragment.newInstance(1, id);
        FragmentUtils.replace(getSupportFragmentManager(), orderFragment, R.id.order_details);
        FragmentUtils.replace(getSupportFragmentManager(), taskFragment, R.id.task_allot);

        boolean order = getIntent().getExtras().getBoolean("order", false);//如果是订单跳进来，则没有父级任务
//        if (order) {
//            shangjiTaskBar.setVisibility(View.GONE);
//            shangjiLayout.setVisibility(View.GONE);
//            taskFragment.setIsOrder(true);
//            getInfo();
//        } else {
//            getOrderByTaskId();
//            taskFragment.setIsOrder(false);
//        }
    }


    @OnClick(R.id.shangji_task_bar)
    public void BarClick() {
        if (shangjiLayout.getVisibility() == View.VISIBLE) {
            shangjiLayout.setVisibility(View.GONE);
            shangjiTaskCheck.setChecked(true);
        } else {
            shangjiLayout.setVisibility(View.VISIBLE);
            shangjiTaskCheck.setChecked(false);
        }
    }


    /**
     * 根据订单ID获取信息
     */
    public void getInfo() {
        IdRequest request = new IdRequest();
        request.setId(id);
        HttpServerImpl.getInfoByOrderId(request).subscribe(new HttpResultSubscriber<OrderAndTaskInfoBO>() {
            @Override
            public void onSuccess(OrderAndTaskInfoBO s) {
//                orderFragment.setOrderInfo(s.getOrder());
                taskFragment.setData(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 根据任务id获取订单数据
     */
    public void getOrderByTaskId() {
        IdRequest request = new IdRequest();
        request.setId(id);
        HttpServerImpl.getInfoByTaskId(request).subscribe(new HttpResultSubscriber<OrderAndTaskInfoBO>() {
            @Override
            public void onSuccess(OrderAndTaskInfoBO s) {
//                orderFragment.setOrderInfo(s.getOrder());
                taskFragment.setData(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

}
