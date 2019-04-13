package com.wul.oa_article.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.module.order_details.Order_detailsFragment;
import com.wul.oa_article.module.task_accept.Task_acceptFragment;

import butterknife.BindView;


/**
 * 接受任务界面
 */

public class AcceptedTaskActivity extends BaseActivity {

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

    @Override
    protected int getLayout() {
        return R.layout.act_accepted_task;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("接受任务");

        FragmentUtils.replace(getSupportFragmentManager(), new Order_detailsFragment(), R.id.order_details);
        FragmentUtils.replace(getSupportFragmentManager(), new Task_acceptFragment(), R.id.accept_task);
    }
}
