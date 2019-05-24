package com.article.oa_article.view.bumenmanager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;

import com.article.oa_article.R;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.bean.LableBo;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.widget.PopTaskMsg;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 部门管理
 */

public class BumenManagerActivity extends MVPBaseActivity<BumenManagerContract.View, BumenManagerPresenter>
        implements BumenManagerContract.View {

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.bumen_recycle)
    RecyclerView bumenRecycle;

    @Override
    protected int getLayout() {
        return R.layout.act_bumen_manager;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("部门管理");

        initView();
        setListener();
        mPresenter.getBumenList("");
    }


    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        bumenRecycle.setLayoutManager(manager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        bumenRecycle.addItemDecoration(itemDecoration);
    }


    @OnClick(R.id.add_bumen)
    public void addBuMen() {
        PopTaskMsg popTaskMsg = new PopTaskMsg(this, "新增部门", "部门名", "请输入部门名");
        popTaskMsg.setListener(new PopTaskMsg.onCommitListener() {
            @Override
            public void commit(String text) {
                editName.setText("");
                mPresenter.addBuMen(text);
            }

            @Override
            public void update(String text, LableBo.CustomLabelsBean customLabelsBean) {

            }
        });
        popTaskMsg.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }


    private void setListener() {
        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.getBumenList(editable.toString());
            }
        });
    }


    @Override
    public void getBumenList(List<BuMenFlowBO> bumens) {
        LGRecycleViewAdapter<BuMenFlowBO> adapter = new LGRecycleViewAdapter<BuMenFlowBO>(bumens) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_bumen_manager;
            }

            @Override
            public void convert(LGViewHolder holder, BuMenFlowBO buMenFlowBO, int position) {
                holder.setText(R.id.bumen_name, buMenFlowBO.getName() + "（" + buMenFlowBO.getUsers() + "）");
            }
        };
        bumenRecycle.setAdapter(adapter);
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }
}
