package com.article.oa_article.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.base.BaseActivity;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.ComplanOrderBo;
import com.article.oa_article.bean.HistoryBO;
import com.article.oa_article.bean.request.ComplayRequest;
import com.article.oa_article.bean.request.SelectRequest;
import com.article.oa_article.view.order_details.Order_detailsActivity;
import com.article.oa_article.widget.AlertDialog;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索订单页面
 */
public class SelectOrderActivity extends BaseActivity {


    @BindView(R.id.history_recycle)
    RecyclerView recycleView;
    @BindView(R.id.history_layout)
    LinearLayout historyLayout;
    @BindView(R.id.edit_select)
    EditText editSelect;
    @BindView(R.id.recycle_view)
    RecyclerView taskRecycle;
    @BindView(R.id.task_layout)
    LinearLayout taskLayout;
    @BindView(R.id.none_layout)
    LinearLayout noneLayout;
    @BindView(R.id.bar_order_message)
    TextView barOrderMessage;
    @BindView(R.id.bar_order_type)
    TextView barOrderType;
    @BindView(R.id.bar_person_time)
    TextView barPersonTime;
    @BindView(R.id.shengyu_time)
    TextView shengyuTime;
    @BindView(R.id.renwu_time)
    TextView renwuTime;
    @BindView(R.id.no_cancle_layout)
    LinearLayout noCancleLayout;
    @BindView(R.id.cancle_layout)
    LinearLayout cancleLayout;

    @Override
    protected int getLayout() {
        return R.layout.act_select;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("搜索订单");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        taskRecycle.setLayoutManager(manager1);

        barOrderMessage.setText("创建人");
        barOrderType.setText("客户简称");
        barPersonTime.setText("任务时限");
        renwuTime.setText("订单交期");
        shengyuTime.setText("剩余时间");
//            shengyuTime.setGravity(Gravity.LEFT);
//            taskLayout.setGravity(Gravity.CENTER_VERTICAL);
        noCancleLayout.setVisibility(View.VISIBLE);
        cancleLayout.setVisibility(View.GONE);
        setListerner();
        getHistoryList();
    }


    private void setListerner() {
        editSelect.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH || (null != keyEvent && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                if (null != keyEvent) {
                    switch (keyEvent.getAction()) {
                        case KeyEvent.ACTION_UP:
                            selectTask();
                            return true;
                        default:
                            return true;
                    }
                } else {
                    selectTask();
                }
            }
            return true;
        });
    }


    /**
     * 获取历史搜索记录
     */
    private void getHistoryList() {
        SelectRequest request = new SelectRequest();
        request.setPageNum(1 + "");
        request.setPageSize(1000 + "");
        String commonId;
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            commonId = "0";
        } else {
            commonId = MyApplication.userBo.getCompanys().get(0).getId() + "";
        }
        request.setCompanyId(commonId);
        request.setUserId(MyApplication.userBo.getId() + "");
        request.setType(0 + "");
        HttpServerImpl.selectHistory(request).subscribe(new HttpResultSubscriber<List<HistoryBO>>() {
            @Override
            public void onSuccess(List<HistoryBO> s) {
                showAdapter(s);
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }

    /**
     * 显示搜索记录
     */
    private void showAdapter(List<HistoryBO> s) {
        List<HistoryBO> list = new ArrayList<>();
        if (s.size() > 5) {
            for (int i = 0; i < 5; i++) {
                list.add(s.get(i));
            }
        } else {
            list = s;
        }
        LGRecycleViewAdapter<HistoryBO> adapter = new LGRecycleViewAdapter<HistoryBO>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_select_history;
            }

            @Override
            public void convert(LGViewHolder holder, HistoryBO s, int position) {
                holder.setText(R.id.history_text, s.getContent());
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                editSelect.setText(s.get(position).getContent());
                selectTask();
            }
        });
        recycleView.setAdapter(adapter);
    }


    @OnClick(R.id.clear_history)
    public void clearHistory(View view) {
        new AlertDialog(this).builder().setGone().setMsg("是否确认情况搜索历史？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", v -> clearHistory()).show();
    }


    /**
     * 清除搜索历史
     */
    private void clearHistory() {
        SelectRequest request = new SelectRequest();
        String commonId;
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            commonId = "0";
        } else {
            commonId = MyApplication.userBo.getCompanys().get(0).getId() + "";
        }
        request.setCompanyId(commonId);
        request.setType(0 + "");
        HttpServerImpl.clearHistory(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                showToast("清除成功！");
                getHistoryList();
            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    /**
     * 搜索任务信息
     */
    private void selectTask() {
        ComplayRequest request = new ComplayRequest();
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
        request.setKeyWord(editSelect.getText().toString().trim());
        getOrderByTask(request);
    }


    /**
     * 搜索任务列表
     */
    private void getOrderByTask(ComplayRequest request) {
        showProgress();
        request.setType("0");
        HttpServerImpl.getComplayList(request).subscribe(new HttpResultSubscriber<List<ComplanOrderBo>>() {
            @Override
            public void onSuccess(List<ComplanOrderBo> s) {
                stopProgress();
                historyLayout.setVisibility(View.GONE);
                if (s == null || s.size() == 0) {
                    taskLayout.setVisibility(View.GONE);
                    noneLayout.setVisibility(View.VISIBLE);
                } else {
                    taskLayout.setVisibility(View.VISIBLE);
                    noneLayout.setVisibility(View.GONE);
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
                holder.setText(R.id.order_message, myOrderBO.getCreateName().length() > 3 ?
                        myOrderBO.getCreateName().substring(0, 3) + "..." : myOrderBO.getCreateName());
                holder.setText(R.id.order_type, myOrderBO.getClientName());
                holder.setText(R.id.order_nick_name, StringUtils.isEmpty(myOrderBO.getUserName()) ? "--" :
                        (myOrderBO.getUserName().length() > 3 ? myOrderBO.getUserName().substring(0, 3) + "..." : myOrderBO.getUserName()));
                if (StringUtils.isEmpty(myOrderBO.getTaskPlanDate())) {
                    holder.setText(R.id.nike_name_time, "--");
                } else {
                    holder.setText(R.id.nike_name_time, myOrderBO.getTaskPlanDate().replaceAll("-", "/"));
                }
                TextView task_date = (TextView) holder.getView(R.id.task_date);
//                if (myOrderBO.getTaskDate() == 0) {
//                    task_date.setText("");
//                } else
                if (StringUtils.isEmpty(myOrderBO.getTaskDate())) {
                    task_date.setText("");
                } else if (Integer.parseInt(myOrderBO.getTaskDate()) > 0) {
                    String taskDate = String.valueOf(Math.abs(Integer.parseInt(myOrderBO.getTaskDate())));
                    if (Integer.parseInt(myOrderBO.getTaskDate()) > 1000) {
                        taskDate = String.valueOf(Math.abs(Integer.parseInt(myOrderBO.getTaskDate()))).substring(0, 2) + "...";
                    }
                    task_date.setText(taskDate);
                    task_date.setTextColor(Color.parseColor("#71EA45"));
                } else {
                    String taskDate = String.valueOf(Math.abs(Integer.parseInt(myOrderBO.getTaskDate())));
                    if (Integer.parseInt(myOrderBO.getTaskDate()) < -1000) {
                        taskDate = String.valueOf(Math.abs(Integer.parseInt(myOrderBO.getTaskDate()))).substring(0, 2) + "...";
                    }
                    task_date.setText(taskDate);
                    task_date.setTextColor(Color.parseColor("#E92B2B"));
                }
                holder.setText(R.id.task_time, myOrderBO.getOrderPlanDate().replaceAll("-", "/")
                        .replaceAll(" ", ""));
                TextView surplus_time = (TextView) holder.getView(R.id.surplus_time);
                if (StringUtils.isEmpty(myOrderBO.getOrderDate())) {
                    surplus_time.setText(myOrderBO.getOrderEndDate().replaceAll("-", "/"));
                    surplus_time.setTextColor(Color.parseColor("#8D8C91"));
                    surplus_time.setTextSize(11);
                } else {
                    surplus_time.setTextSize(16);
                    surplus_time.setText(myOrderBO.getOrderDate() + "天");
                    if (Integer.parseInt(myOrderBO.getOrderDate()) > 0) {
                        surplus_time.setTextColor(Color.parseColor("#71EA45"));
                    } else {
                        surplus_time.setTextColor(Color.parseColor("#E92B2B"));
                    }
                }
                holder.getView(R.id.cancle_img).setVisibility(View.GONE);
                switch (myOrderBO.getStatus()) {
                    case 2:   //已完成
                        task_date.setText("");
                        holder.setText(R.id.nike_name_time, "--");
                        holder.setText(R.id.order_nick_name, "--");
                        holder.getView(R.id.cancle_img).setVisibility(View.VISIBLE);
                        holder.setImageResurce(R.id.cancle_img, R.drawable.order_suress_img);
                        break;
                    case 3:   //已取消
                        surplus_time.setText("");
                        holder.getView(R.id.cancle_img).setVisibility(View.VISIBLE);
                        holder.setImageResurce(R.id.cancle_img, R.drawable.yi_cancle);
                        break;
                    default:
                        break;
                }
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", s.get(position).getOrderId());
//            if (s.get(position).getCreateId().equals(MyApplication.userBo.getId())
//                    && s.get(position).getStatus() == 1) {  //如果是当前用户创建,且进行中
//                gotoActivity(CreateTaskActivity.class, bundle, false);
//            } else {
            bundle.putBoolean("isOrder", true);
            gotoActivity(Order_detailsActivity.class, bundle, false);
//            }
        });
        taskRecycle.setAdapter(adapter);
    }
}
