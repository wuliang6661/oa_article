package com.wul.oa_article.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
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
 * 分派任务界面
 */
public class FenPaiActivity extends BaseActivity {

    @BindView(R.id.order_details)
    FrameLayout orderDetails;
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
    @BindView(R.id.accept_task)
    FrameLayout acceptTask;

    private int taskId;

    Order_detailsFragment detailsFragment;
    Task_allotFragment taskFragment;

    @Override
    protected int getLayout() {
        return R.layout.act_accepted_task;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("分派任务");

        taskId = getIntent().getExtras().getInt("taskId");
        detailsFragment = Order_detailsFragment.newInstance(2, taskId);
        taskFragment = Task_allotFragment.newInstance(0, taskId);
        FragmentUtils.replace(getSupportFragmentManager(), detailsFragment, R.id.order_details);
        FragmentUtils.replace(getSupportFragmentManager(), taskFragment, R.id.accept_task);
        getOrderByTaskId();
    }


    /**
     * 根据任务id获取订单数据
     */
    public void getOrderByTaskId() {
        IdRequest request = new IdRequest();
        request.setId(taskId);
        HttpServerImpl.getInfoByTaskId(request).subscribe(new HttpResultSubscriber<OrderAndTaskInfoBO>() {
            @Override
            public void onSuccess(OrderAndTaskInfoBO s) {
                detailsFragment.setOrderInfo(s.getOrder());
                taskFragment.setData(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    private void setShangjiLayout() {
//        if (taskBO.getParentTask() == null) {
//            shangjiLayout.setVisibility(View.GONE);
//            shangjiTaskBar.setVisibility(View.GONE);
//        } else {
//            shangjiTaskBar.setVisibility(View.VISIBLE);
//            shangjiLayout.setVisibility(View.VISIBLE);
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
}
