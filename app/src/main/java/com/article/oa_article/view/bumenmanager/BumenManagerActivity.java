package com.article.oa_article.view.bumenmanager;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;

import com.article.oa_article.R;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.bean.LableBo;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.widget.AlertDialog;
import com.article.oa_article.widget.PopTaskMsg;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;
import com.blankj.utilcode.util.SizeUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

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
    @BindView(R.id.bumen_recycle_view)
    SwipeMenuRecyclerView bumenRecycle;

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
        bumenRecycle.setSwipeMenuCreator(mSwipeMenuCreator);
        SwipeMenuItemClickListener mMenuItemClickListener = menuBridge -> {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            new AlertDialog(BumenManagerActivity.this).builder().setGone().setMsg("是否确认删除部门？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", v -> mPresenter.deleteDeart(bumens.get(menuBridge.getAdapterPosition()).getId())).show();
        };
        // 菜单点击监听。
        bumenRecycle.setSwipeMenuItemClickListener(mMenuItemClickListener);
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

    List<BuMenFlowBO> bumens;

    @Override
    public void getBumenList(List<BuMenFlowBO> bumens) {
        this.bumens = bumens;
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
        adapter.setOnItemClickListener(R.id.item_layout, (view, position) -> {
            PopTaskMsg popTaskMsg = new PopTaskMsg(BumenManagerActivity.this, "部门", "部门名", "请输入部门名");
            popTaskMsg.setText(bumens.get(position).getName(), null);
            popTaskMsg.setListener(new PopTaskMsg.onCommitListener() {
                @Override
                public void commit(String text) {
                    mPresenter.updateBumenName(bumens.get(position).getId(), text);
                }

                @Override
                public void update(String text, LableBo.CustomLabelsBean customLabelsBean) {

                }
            });
            popTaskMsg.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        });
        bumenRecycle.setAdapter(adapter);
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }
}
