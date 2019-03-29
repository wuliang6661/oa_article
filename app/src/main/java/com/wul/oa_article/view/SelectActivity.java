package com.wul.oa_article.view;

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

import com.blankj.utilcode.util.StringUtils;
import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.HistoryBO;
import com.wul.oa_article.bean.MyOrderBO;
import com.wul.oa_article.bean.request.OrderRequest;
import com.wul.oa_article.bean.request.SelectRequest;
import com.wul.oa_article.widget.AlertDialog;
import com.wul.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.wul.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectActivity extends BaseActivity {


    @BindView(R.id.clear_history)
    TextView clearHistory;
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

    @Override
    protected int getLayout() {
        return R.layout.act_select;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("搜索任务");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        taskRecycle.setLayoutManager(manager1);
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
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            showToast("当前用户没有公司！");
            return;
        }
        request.setCompanyId(MyApplication.userBo.getCompanys().get(0).getId() + "");
        request.setUserId(MyApplication.userBo.getId() + "");
        request.setType(1 + "");
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
        LGRecycleViewAdapter<HistoryBO> adapter = new LGRecycleViewAdapter<HistoryBO>(s) {
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
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            showToast("当前用户没有公司！");
            return;
        }
        request.setCompanyId(MyApplication.userBo.getCompanys().get(0).getId() + "");
        request.setType(1 + "");
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
        OrderRequest request = new OrderRequest();
        request.setPageNum(1);
        request.setPageSize(1000);
        request.setUserId(MyApplication.userBo.getId() + "");
        if (MyApplication.userBo.getCompanys() == null || MyApplication.userBo.getCompanys().size() == 0) {
            showToast("当前用户没有公司！");
            return;
        }
        request.setCompanyId(MyApplication.userBo.getCompanys().get(0).getId() + "");
        request.setKeyWord(editSelect.getText().toString().trim());
        getOrderByTask(request);

    }


    /**
     * 搜索任务列表
     */
    private void getOrderByTask(OrderRequest request) {
        showProgress();
        request.setType("0");
        HttpServerImpl.getOrderByTask(request).subscribe(new HttpResultSubscriber<List<MyOrderBO>>() {
            @Override
            public void onSuccess(List<MyOrderBO> s) {
                stopProgress();
                historyLayout.setVisibility(View.GONE);
                taskLayout.setVisibility(View.VISIBLE);
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
        taskRecycle.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
