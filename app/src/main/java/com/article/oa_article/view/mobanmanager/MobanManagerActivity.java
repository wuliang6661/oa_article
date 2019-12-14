package com.article.oa_article.view.mobanmanager;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.article.oa_article.view.createmoban.CreateMoBanActivity;
import com.article.oa_article.widget.AlertDialog;
import com.blankj.utilcode.util.SizeUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

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

    MoBanAdapter adapter;

    private int firstId, scondId;

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
        itemTouchHelper.attachToRecyclerView(recycleView);
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


    //在主类中
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//允许上下拖动
//            int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//允许左右拖动 只写左就只允许左拖动
            return makeMovementFlags(dragFlags, ItemTouchHelper.ACTION_STATE_IDLE);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            Log.e("wuliang", viewHolder.getAdapterPosition() + " -----" + viewHolder1.getAdapterPosition());
            adapter.onItemMove(viewHolder.getAdapterPosition(), viewHolder1.getAdapterPosition());
            if (viewHolder.getAdapterPosition() != viewHolder1.getAdapterPosition()) {
                firstId = viewHolder.getAdapterPosition();
                scondId = viewHolder1.getAdapterPosition();
            }
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            adapter.onItemDissmiss(viewHolder.getAdapterPosition());
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            switch (actionState) {
                case ItemTouchHelper.ACTION_STATE_IDLE:
                    if (firstId != scondId) {
                        mPresenter.moveTemplate(templeteBOS.get(firstId).getId(), templeteBOS.get(scondId).getId());
                    }
                    break;
            }
        }
    });


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
//        recycleView.setOnItemStateChangedListener(new OnItemStateChangedListener() {
//            @Override
//            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//                switch (actionState) {
//                    case ItemTouchHelper.ACTION_STATE_IDLE:
//                        if (firstId != scondId) {
//                            mPresenter.moveTemplate(templeteBOS.get(firstId).getId(), templeteBOS.get(scondId).getId());
//                        }
//                        break;
//                }
//            }
//        });
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

    @Override
    public void moveSoruss() {
        mPresenter.getTempleteList(request);
    }


    private void setAdapter() {
        adapter = new MoBanAdapter(templeteBOS, isShowMake);
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
        adapter.setMoveListener((fromPosition, toPosition) -> mPresenter.moveTemplate(templeteBOS.get(fromPosition).getId(), templeteBOS.get(toPosition).getId()));
        recycleView.setAdapter(adapter);
    }


    @OnClick(R.id.add_moban)
    public void addMoBan() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isAdd", true);
        gotoActivity(CreateMoBanActivity.class, bundle, false);
    }

}
