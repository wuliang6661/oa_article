package com.wul.oa_article.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wul.oa_article.R;
import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.BaseActivity;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.request.SelectRequest;
import com.wul.oa_article.widget.AlertDialog;
import com.wul.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.wul.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectActivity extends BaseActivity {


    @BindView(R.id.clear_history)
    TextView clearHistory;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.history_layout)
    LinearLayout historyLayout;

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
        getHistoryList();
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
        request.setCompanyId(MyApplication.userBo.getCompanys().get(0).getId());
        //request.setType(1);
        HttpServerImpl.selectHistory(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

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
    private void showAdapter(){
        LGRecycleViewAdapter<String> adapter = new LGRecycleViewAdapter<String>(new ArrayList<>()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_select_history;
            }

            @Override
            public void convert(LGViewHolder holder, String s, int position) {

            }
        };
    }



    @OnClick(R.id.clear_history)
    public void clearHistory(View view) {
        new AlertDialog(this).builder().setGone().setMsg("是否确认情况搜索历史？")
                .setNegativeButton("取消", null)
                .setPositiveButton("拨打", v -> clearHistory()).show();
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
        request.setCompanyId(MyApplication.userBo.getCompanys().get(0).getId());
        HttpServerImpl.clearHistory(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String message) {
                showToast(message);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
