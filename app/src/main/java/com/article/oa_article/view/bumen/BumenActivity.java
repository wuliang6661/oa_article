package com.article.oa_article.view.bumen;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.mvp.MVPBaseActivity;
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
    }

    @OnClick(R.id.next_button)
    public void commit() {
        Intent intent = new Intent();
        intent.putExtra("bumen", buMenFlowBO);
        setResult(0x11, intent);
        finish();
    }


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getBumenFlows(List<BuMenFlowBO> buMenFlowBOS) {
        FlowAdapter adapter = new FlowAdapter(buMenFlowBOS);
        adapter.setOnItemClickListener(R.id.flow_text, (view, position) -> {
            adapter.setSelectPosition(position);
            adapter.notifyDataSetChanged();
            buMenFlowBO = buMenFlowBOS.get(position);
        });
        tagRecycle.setAdapter(adapter);
        buMenFlowBO = buMenFlowBOS.get(0);
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
