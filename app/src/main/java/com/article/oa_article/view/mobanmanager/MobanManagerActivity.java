package com.article.oa_article.view.mobanmanager;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.MuBanTaskBO;
import com.article.oa_article.bean.TempleteBO;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.TempleteRequest;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.view.createmoban.CreateMoBanActivity;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import org.greenrobot.eventbus.EventBus;

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

    private boolean isShowMake = true;   //是否显示使用按钮

    @Override
    protected int getLayout() {
        return R.layout.act_moban_manager;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goBack();
        setTitleText("模板管理");

        isShowMake = getIntent().getBooleanExtra("isShowMake", true);
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

    @Override
    public void makeMuBanSoress(List<MuBanTaskBO> muBanTaskBOS) {
//        EventBus.getDefault().post(muBanTaskBOS);
        Intent intent = new Intent();
        DataBean dataBean = new DataBean();
        dataBean.setMubans(muBanTaskBOS);
        intent.putExtra("data", dataBean);
        setResult(0X11, intent);
        finish();
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
                if (isShowMake) {
                    holder.getView(R.id.select_button).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.select_button).setVisibility(View.GONE);
                }
            }
        };
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isAdd", false);
            bundle.putSerializable("templete", templeteBOS.get(position));
            bundle.putInt("id", templeteBOS.get(position).getId());
            gotoActivity(CreateMoBanActivity.class, bundle, false);
        });
        adapter.setOnItemClickListener(R.id.select_button, (view, position) -> {
            IdRequest request = new IdRequest();
            request.setId(templeteBOS.get(position).getId());
            mPresenter.makeMuBan(request);
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
