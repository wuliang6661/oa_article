package com.article.oa_article.view.myscope;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.article.oa_article.R;
import com.article.oa_article.bean.ScopeBO;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;


/**
 * MVPPlugin
 * 收到评价
 */

public class MyScopeActivity extends MVPBaseActivity<MyScopeContract.View, MyScopePresenter>
        implements MyScopeContract.View {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;

    @Override
    protected int getLayout() {
        return R.layout.act_my_scope;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("收到评价");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(this), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.divider_inset)));
        recycleView.addItemDecoration(itemDecoration);
        mPresenter.getScopeList();
    }


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getScopeList(List<ScopeBO> s) {
        setAdapter(s);
    }

    private void setAdapter(List<ScopeBO> s) {
        LGRecycleViewAdapter<ScopeBO> adapter = new LGRecycleViewAdapter<ScopeBO>(s) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_myscope;
            }

            @Override
            public void convert(LGViewHolder holder, ScopeBO scopeBO, int position) {
                holder.setText(R.id.order_name, scopeBO.getClientOrderName());
                holder.setText(R.id.order_num, scopeBO.getClientOrderNum());
                holder.setText(R.id.create_name, scopeBO.getClientName().length() > 3 ?
                        scopeBO.getClientName().substring(0, 3) + "..." : scopeBO.getClientName());
                holder.getView(R.id.create_nick_name).setVisibility(View.GONE);
                holder.setText(R.id.order_time, scopeBO.getScore() + "");
            }
        };
        recycleView.setAdapter(adapter);
    }

}
