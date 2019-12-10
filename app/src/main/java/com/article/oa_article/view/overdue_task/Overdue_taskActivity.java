package com.article.oa_article.view.overdue_task;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.TaskServiceImpl;
import com.article.oa_article.bean.MyOrderBO;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.view.AcceptedTaskActivity;
import com.article.oa_article.view.MyOrderActivity;
import com.article.oa_article.view.order_details.Order_detailsActivity;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.StringUtils;

import java.util.List;

import butterknife.BindView;


/**
 * MVPPlugin
 * 逾期任务界面
 */

public class Overdue_taskActivity extends MVPBaseActivity<Overdue_taskContract.View, Overdue_taskPresenter> implements Overdue_taskContract.View {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.shengyu_time)
    TextView shengyuTime;

    private int userId;

    @Override
    protected int getLayout() {
        return R.layout.act_overdue;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("逾期任务");
        shengyuTime.setText("逾期时间");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        userId = getIntent().getExtras().getInt("userId");
        getData();
    }


    private void getData() {
        TaskServiceImpl.getOverdueTask(userId).subscribe(new HttpResultSubscriber<List<MyOrderBO>>() {
            @Override
            public void onSuccess(List<MyOrderBO> myOrderBOS) {
                setAdapter(myOrderBOS);
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
                surplus_time.setText(myOrderBO.getOverdue() + "天");
                surplus_time.setTextSize(16);
                surplus_time.setTextColor(Color.parseColor("#E92B2B"));
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, (view, i) -> {
            MyOrderBO orderBO = s.get(i);
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
        });
        recycleView.setAdapter(adapter);
    }

}
