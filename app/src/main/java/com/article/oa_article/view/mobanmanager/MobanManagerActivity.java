package com.article.oa_article.view.mobanmanager;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.MuBanTaskBO;
import com.article.oa_article.bean.TempleteBO;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.TempleteRequest;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.view.bumenmanager.BumenManagerActivity;
import com.article.oa_article.view.createmoban.CreateMoBanActivity;
import com.article.oa_article.widget.AlertDialog;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.SizeUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

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
    SwipeMenuRecyclerView recycleView;
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
        setSwipeMenu();
    }


    private void setSwipeMenu() {
        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, viewType) -> {
            // 2 删除
            SwipeMenuItem deleteItem = new SwipeMenuItem(this);
            deleteItem.setText("删除")
                    .setBackgroundColor(getResources().getColor(R.color.item_delete))
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(15) // 文字大小。
                    .setWidth(SizeUtils.dp2px(63))
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            rightMenu.addMenuItem(deleteItem);
            // 注意：哪边不想要菜单，那么不要添加即可。
        };
        // 设置监听器。
        recycleView.setSwipeMenuCreator(mSwipeMenuCreator);
        SwipeMenuItemClickListener mMenuItemClickListener = menuBridge -> {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            new AlertDialog(MobanManagerActivity.this).builder().setGone().setMsg("是否确认删除该模板？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", v -> mPresenter.deleteTempter(templeteBOS.get(menuBridge.getAdapterPosition()).getId())).show();
        };
        // 菜单点击监听。
        recycleView.setSwipeMenuItemClickListener(mMenuItemClickListener);
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

    @Override
    public void deleteSourss() {
        mPresenter.getTempleteList(request);
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
