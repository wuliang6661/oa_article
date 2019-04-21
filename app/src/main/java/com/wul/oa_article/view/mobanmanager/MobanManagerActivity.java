package com.wul.oa_article.view.mobanmanager;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.wul.oa_article.R;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.TempleteBO;
import com.wul.oa_article.bean.request.TempleteRequest;
import com.wul.oa_article.mvp.MVPBaseActivity;
import com.wul.oa_article.view.createmoban.CreateMoBanActivity;
import com.wul.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.wul.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 模板管理
 */

public class MobanManagerActivity extends MVPBaseActivity<MobanManagerContract.View, MobanManagerPresenter>
        implements MobanManagerContract.View {

    @BindView(R.id.add_moban)
    TextView addMoban;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.edit_moban_name)
    EditText editMobanName;

    private List<TempleteBO> templeteBOS;
    TempleteRequest request;

    @Override
    protected int getLayout() {
        return R.layout.act_moban_manager;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitleText("模板管理");

        request = new TempleteRequest();
        request.setCompanyId(Integer.parseInt(MyApplication.getCommonId()));
        request.setName("");
        request.setPageNum(1);
        request.setPageSize(1000);
        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getTempleteList(request);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(manager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        recycleView.addItemDecoration(itemDecoration);
        editMobanName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                request.setName(editable.toString());
                mPresenter.getTempleteList(request);
            }
        });
    }


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getMoBan(List<TempleteBO> templeteBOS) {
        this.templeteBOS = templeteBOS;
        setAdapter();
    }


    private void setAdapter() {
        LGRecycleViewAdapter<TempleteBO> adapter = new LGRecycleViewAdapter<TempleteBO>(templeteBOS) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_moban;
            }

            @Override
            public void convert(LGViewHolder holder, TempleteBO templeteBO, int position) {
                holder.setText(R.id.xuhao_text, position + 1 + "");
                holder.setText(R.id.moban_title, templeteBO.getName());
                holder.setText(R.id.moban_message, templeteBO.getRemarks());
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isAdd", false);
            bundle.putSerializable("templete", templeteBOS.get(position));
            bundle.putInt("id", templeteBOS.get(position).getId());
            gotoActivity(CreateMoBanActivity.class, bundle, false);
        });
        recycleView.setAdapter(adapter);
    }


    @OnClick(R.id.add_moban)
    public void addMoBan() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isAdd", true);
        gotoActivity(CreateMoBanActivity.class, bundle, false);
    }

}
