package com.article.oa_article.view.lablecustom;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.LableBo;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.article.oa_article.widget.AlertDialog;
import com.article.oa_article.widget.PopTaskMsg;
import com.article.oa_article.widget.lgrecycleadapter.LGRecycleViewAdapter;
import com.article.oa_article.widget.lgrecycleadapter.LGViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 自定义标签页面
 */

public class LableCustomActivity extends MVPBaseActivity<LableCustomContract.View, LableCustomPresenter>
        implements LableCustomContract.View {

    @BindView(R.id.flow_layout)
    RecyclerView flowLayout;
    @BindView(R.id.lable_flow_layout)
    RecyclerView lableFlowLayout;
    @BindView(R.id.add_pinglei)
    LinearLayout addPinglei;
    @BindView(R.id.next_button)
    Button nextButton;

    LableBo lableBo;

    boolean isFirst = true;   //默认选中上面的第一个

    LableBo.SysLabelsBean labelsBean;
    LableBo.CustomLabelsBean customLabelsBean;

    SysAdapter sysAdapter;
    MyLableAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.act_lable_custom;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("标签选择");

        initView();
        mPresenter.getAllLables();
    }


    private void initView() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        flowLayout.setLayoutManager(manager);

        GridLayoutManager manager1 = new GridLayoutManager(this, 3);
        lableFlowLayout.setLayoutManager(manager1);
    }


    @OnClick(R.id.next_button)
    public void nextClick() {
        if (isFirst) {
            Intent intent = new Intent();
            intent.putExtra("lable", labelsBean);
            intent.putExtra("isFirst", isFirst);
            setResult(0x22, intent);
        } else {
            Intent intent = new Intent();
            intent.putExtra("lable", customLabelsBean);
            intent.putExtra("isFirst", isFirst);
            setResult(0x22, intent);
        }
        finish();
    }


    @OnClick(R.id.add_pinglei)
    public void addClick() {
        PopTaskMsg popTaskMsg = new PopTaskMsg(this, "新增标签", "标签名", "请输入标签名");
        popTaskMsg.setListener(new PopTaskMsg.onCommitListener() {
            @Override
            public void commit(String text) {
                mPresenter.addLable(text);
            }

            @Override
            public void update(String text, LableBo.CustomLabelsBean customLabelsBean) {

            }
        });
        popTaskMsg.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getLable(LableBo lableBo) {
        this.lableBo = lableBo;
        setSysAdapter();
        setMyLableAdapter();
    }

    /**
     * 设置常用标签列表
     */
    private void setSysAdapter() {
        sysAdapter = new SysAdapter(lableBo.getSysLabels());
        sysAdapter.setOnItemClickListener(R.id.flow_text, (view, position) -> {
            isFirst = true;
            if (position == sysAdapter.selectPosition) {
                sysAdapter.setSelectPosition(-1);
                labelsBean = null;
            } else {
                sysAdapter.setSelectPosition(position);
                labelsBean = lableBo.getSysLabels().get(position);
            }
            adapter.setSelectPosition(-1);
            sysAdapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
        });
        flowLayout.setAdapter(sysAdapter);
        if (!lableBo.getSysLabels().isEmpty()) {
            labelsBean = lableBo.getSysLabels().get(0);
        }
    }


    /**
     * 设置自定义标签列表
     */
    private void setMyLableAdapter() {
        adapter = new MyLableAdapter(lableBo.getCustomLabels());
        adapter.setOnItemClickListener(R.id.flow_text, (view, position) -> {
            isFirst = false;
            if (position == adapter.selectPosition) {
                adapter.setSelectPosition(-1);
                customLabelsBean = null;
            } else {
                adapter.setSelectPosition(position);
                customLabelsBean = lableBo.getCustomLabels().get(position);
            }
            sysAdapter.setSelectPosition(-1);
            adapter.notifyDataSetChanged();
            sysAdapter.notifyDataSetChanged();
        });
        adapter.setOnItemClickListener(R.id.flow_delete, (view, position) ->
                new AlertDialog(LableCustomActivity.this).builder().setGone().setMsg("是否确定删除标签？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", v -> mPresenter.deleteLable(lableBo.getCustomLabels().get(position).getId())).show());

        lableFlowLayout.setAdapter(adapter);
    }


    class SysAdapter extends LGRecycleViewAdapter<LableBo.SysLabelsBean> {

        SysAdapter(List<LableBo.SysLabelsBean> dataList) {
            super(dataList);
        }

        int selectPosition = 0;

        void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
        }


        @Override
        public int getLayoutId(int viewType) {
            return R.layout.item_bumen_flow;
        }

        @Override
        public void convert(LGViewHolder holder, LableBo.SysLabelsBean sysLabelsBean, int position) {
            TextView bumenText = (TextView) holder.getView(R.id.flow_text);
            bumenText.setText(sysLabelsBean.getName());
            if (isFirst && position == selectPosition) {
                bumenText.setTextColor(ContextCompat.getColor(LableCustomActivity.this, R.color.blue_color));
                bumenText.setBackgroundResource(R.drawable.menu_item_select);
            } else {
                bumenText.setTextColor(ContextCompat.getColor(LableCustomActivity.this, R.color.tab_txt_color));
                bumenText.setBackgroundResource(R.drawable.menu_item_bg);
            }
        }
    }


    class MyLableAdapter extends LGRecycleViewAdapter<LableBo.CustomLabelsBean> {

        MyLableAdapter(List<LableBo.CustomLabelsBean> dataList) {
            super(dataList);
        }

        int selectPosition = -1;

        void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
        }


        @Override
        public int getLayoutId(int viewType) {
            return R.layout.item_bumen_flow;
        }

        @Override
        public void convert(LGViewHolder holder, LableBo.CustomLabelsBean sysLabelsBean, int position) {
            TextView bumenText = (TextView) holder.getView(R.id.flow_text);
            bumenText.setText(sysLabelsBean.getName());
            if (!isFirst && position == selectPosition) {
                bumenText.setTextColor(ContextCompat.getColor(LableCustomActivity.this, R.color.blue_color));
                bumenText.setBackgroundResource(R.drawable.menu_item_select);

            } else {
                bumenText.setTextColor(ContextCompat.getColor(LableCustomActivity.this, R.color.tab_txt_color));
                bumenText.setBackgroundResource(R.drawable.menu_item_bg);
            }
            holder.getView(R.id.flow_delete).setVisibility(View.VISIBLE);
            bumenText.setOnLongClickListener(view -> {
                PopTaskMsg popTaskMsg = new PopTaskMsg(LableCustomActivity.this, "修改标签", "标签名", "请输入标签名");
                popTaskMsg.setListener(new PopTaskMsg.onCommitListener() {
                    @Override
                    public void commit(String text) {
                        mPresenter.addLable(text);
                    }

                    @Override
                    public void update(String text, LableBo.CustomLabelsBean customLabelsBean) {
                        mPresenter.updateLable(text, customLabelsBean.getOrderNum(), customLabelsBean.getId());
                    }
                });
                popTaskMsg.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                popTaskMsg.setText(sysLabelsBean.getName(), sysLabelsBean);
                return true;
            });
        }
    }


}
