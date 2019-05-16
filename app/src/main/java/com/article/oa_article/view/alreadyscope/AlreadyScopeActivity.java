package com.article.oa_article.view.alreadyscope;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.article.oa_article.R;
import com.article.oa_article.bean.AlreadyScopeBO;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.widget.ScopePopWindow;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;


/**
 * MVPPlugin
 * 已评价列表
 */

public class AlreadyScopeActivity extends MVPBaseActivity<AlreadyScopeContract.View, AlreadyScopePresenter>
        implements AlreadyScopeContract.View {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @Override
    protected int getLayout() {
        return R.layout.act_alreadt_scope;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("已评价");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        mPresenter.getHaveScope();
    }


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getAlreadyScope(List<AlreadyScopeBO> alreadyScopeBOS) {
        LGRecycleViewAdapter<AlreadyScopeBO> adapter = new LGRecycleViewAdapter<AlreadyScopeBO>(alreadyScopeBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_already_scope;
            }

            @Override
            public void convert(LGViewHolder holder, AlreadyScopeBO alreadyScopeBO, int position) {
                holder.setText(R.id.order_name, alreadyScopeBO.getClientOrderName());
                holder.setText(R.id.order_num, alreadyScopeBO.getClientOrderNum());
                holder.setText(R.id.zhixing_person, alreadyScopeBO.getNickName());
                holder.setText(R.id.kehu_name, alreadyScopeBO.getClientName());
                holder.setText(R.id.task_name, alreadyScopeBO.getTaskName());
                holder.setText(R.id.date_text, alreadyScopeBO.getDulDay() + "天");
                holder.setText(R.id.scope, alreadyScopeBO.getScore() + "");
            }
        };
        adapter.setOnItemClickListener(R.id.scope, new LGRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                ScopePopWindow popWindow = new ScopePopWindow(AlreadyScopeActivity.this);
                popWindow.showPop(view, 0, 0);
            }
        });
        recycleView.setAdapter(adapter);
    }
}
