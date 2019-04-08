package com.wul.oa_article.view.orderdetails;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wul.oa_article.R;
import com.wul.oa_article.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OrderDetailsActivity extends MVPBaseActivity<OrderDetailsContract.View, OrderDetailsPresenter>
        implements OrderDetailsContract.View {

    @BindView(R.id.kehu_msg_bar)
    LinearLayout kehuMsgBar;
    @BindView(R.id.create_name)
    TextView createName;
    @BindView(R.id.kehu_name)
    TextView kehuName;
    @BindView(R.id.kehu_order_num)
    TextView kehuOrderNum;
    @BindView(R.id.kehu_order_name)
    TextView kehuOrderName;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.order_danwei)
    TextView orderDanwei;
    @BindView(R.id.order_date)
    TextView orderDate;
    @BindView(R.id.chicun_recycle_view)
    RecyclerView chicunRecycleView;
    @BindView(R.id.beizhu)
    TextView beizhu;
    @BindView(R.id.image_recycle)
    RecyclerView imageRecycle;
    @BindView(R.id.kehu_order_layout)
    LinearLayout kehuOrderLayout;
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
    @BindView(R.id.task_bar)
    LinearLayout taskBar;
    @BindView(R.id.bar_order_name)
    TextView barOrderName;
    @BindView(R.id.bar_order_num)
    TextView barOrderNum;
    @BindView(R.id.bar_order_message)
    TextView barOrderMessage;
    @BindView(R.id.bar_order_type)
    TextView barOrderType;
    @BindView(R.id.shengyu_time)
    TextView shengyuTime;
    @BindView(R.id.renwu_time)
    TextView renwuTime;
    @BindView(R.id.task_recycle_view)
    RecyclerView taskRecycleView;
    @BindView(R.id.task_list_layout)
    LinearLayout taskListLayout;
    @BindView(R.id.kehu_order_check)
    CheckBox kehuOrderCheck;
    @BindView(R.id.shangji_task_check)
    CheckBox shangjiTaskCheck;
    @BindView(R.id.task_check)
    CheckBox taskCheck;

    @Override
    protected int getLayout() {
        return R.layout.act_order_details;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitleText("订单详情");

        initView();
    }


    /**
     * 初始化布局
     */
    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        chicunRecycleView.setLayoutManager(manager);

        GridLayoutManager imageManager = new GridLayoutManager(this, 3);
        imageRecycle.setLayoutManager(imageManager);

        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        taskRecycleView.setLayoutManager(manager1);

        chicunRecycleView.setNestedScrollingEnabled(false);
        imageRecycle.setNestedScrollingEnabled(false);
        taskRecycleView.setNestedScrollingEnabled(false);
    }

    @OnClick({R.id.kehu_msg_bar, R.id.shangji_task_bar, R.id.task_bar})
    public void barOnClick(View view) {
        switch (view.getId()) {
            case R.id.kehu_msg_bar:
                if (kehuOrderLayout.getVisibility() == View.VISIBLE) {
                    kehuOrderLayout.setVisibility(View.GONE);
                    kehuOrderCheck.setChecked(true);
                } else {
                    kehuOrderLayout.setVisibility(View.VISIBLE);
                    kehuOrderCheck.setChecked(false);
                }
                break;
            case R.id.shangji_task_bar:
                if (shangjiLayout.getVisibility() == View.VISIBLE) {
                    shangjiLayout.setVisibility(View.GONE);
                    shangjiTaskCheck.setChecked(true);
                } else {
                    shangjiLayout.setVisibility(View.VISIBLE);
                    shangjiTaskCheck.setChecked(false);
                }
                break;
            case R.id.task_bar:
                if (taskListLayout.getVisibility() == View.VISIBLE) {
                    taskListLayout.setVisibility(View.GONE);
                    taskCheck.setChecked(true);
                } else {
                    taskListLayout.setVisibility(View.VISIBLE);
                    taskCheck.setChecked(false);
                }
                break;
        }
    }


}
