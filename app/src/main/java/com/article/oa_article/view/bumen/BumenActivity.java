package com.article.oa_article.view.bumen;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.bean.LableBo;
import com.article.oa_article.bean.PersonBO;
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
 * 邮箱 784787081@qq.com
 */

public class BumenActivity extends MVPBaseActivity<BumenContract.View, BumenPresenter>
        implements BumenContract.View {


    BuMenFlowBO buMenFlowBO;

    @BindView(R.id.tag_recycle)
    RecyclerView tagRecycle;

    private int type = 0;   //默认选择部门，1未修改联系人部门
    private PersonBO personBO;

    @Override
    protected int getLayout() {
        return R.layout.act_bumen_flow;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("部门选择");

        GridLayoutManager manager = new GridLayoutManager(this, 3);
        tagRecycle.setLayoutManager(manager);
        mPresenter.getBumenList();

        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            personBO = (PersonBO) getIntent().getSerializableExtra("person");
        }
    }

    @OnClick(R.id.next_button)
    public void commit() {
        if (type == 0) {
            Intent intent = new Intent();
            intent.putExtra("bumen", buMenFlowBO);
            setResult(0x11, intent);
            finish();
        } else {
            if (buMenFlowBO != null) {
                mPresenter.updateDeart(personBO.getId(), Integer.parseInt(buMenFlowBO.getId()));
            } else {
                finish();
            }
        }

    }


    @OnClick(R.id.add_pinglei)
    public void addBuMen() {
        PopTaskMsg popTaskMsg = new PopTaskMsg(this, "新增部门", "部门名", "请输入部门名");
        popTaskMsg.setListener(new PopTaskMsg.onCommitListener() {
            @Override
            public void commit(String text) {
                mPresenter.addBuMen(text);
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
    public void getBumenFlows(List<BuMenFlowBO> buMenFlowBOS) {
        FlowAdapter adapter = new FlowAdapter(buMenFlowBOS);

        adapter.setOnItemClickListener(R.id.flow_text, (view, position) -> {
            if (adapter.selectPosition == position) {
                adapter.setSelectPosition(-1);
                buMenFlowBO = null;
            } else {
                adapter.setSelectPosition(position);
                buMenFlowBO = buMenFlowBOS.get(position);
            }
            adapter.notifyDataSetChanged();
        });
        adapter.setOnItemClickListener(R.id.flow_delete, (view, position) ->
                new AlertDialog(BumenActivity.this).builder().setGone().setMsg("是否确认删除部门？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", v -> {
                            mPresenter.deleteDeart(buMenFlowBOS.get(position).getId());
                            if (position == adapter.selectPosition) {
                                adapter.setSelectPosition(-1);
                                buMenFlowBO = null;
                            }
                        }).show()
        );
        tagRecycle.setAdapter(adapter);
        if (!buMenFlowBOS.isEmpty()) {
            buMenFlowBO = buMenFlowBOS.get(0);
        } else {
            buMenFlowBO = null;
        }
    }

    @Override
    public void updateDeats() {
        finish();
    }


    class FlowAdapter extends LGRecycleViewAdapter<BuMenFlowBO> {

        int selectPosition = 0;

        void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
        }


        FlowAdapter(List<BuMenFlowBO> datas) {
            super(datas);
        }

        @Override
        public int getLayoutId(int viewType) {
            return R.layout.item_bumen_flow;
        }

        @Override
        public void convert(LGViewHolder holder, BuMenFlowBO buMenFlowBO, int position) {
            TextView bumenText = (TextView) holder.getView(R.id.flow_text);
            holder.getView(R.id.flow_delete).setVisibility(View.VISIBLE);
            bumenText.setText(buMenFlowBO.getName());
            if (position == selectPosition) {
                bumenText.setTextColor(ContextCompat.getColor(BumenActivity.this, R.color.blue_color));
                bumenText.setBackgroundResource(R.drawable.menu_item_select);
            } else {
                bumenText.setTextColor(ContextCompat.getColor(BumenActivity.this, R.color.tab_txt_color));
                bumenText.setBackgroundResource(R.drawable.menu_item_bg);
            }
        }
    }
}
