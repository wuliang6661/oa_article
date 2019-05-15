package com.article.oa_article.view.bumen;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BumenActivity extends MVPBaseActivity<BumenContract.View, BumenPresenter>
        implements BumenContract.View {

    @BindView(R.id.flow_layout)
    TagFlowLayout flowLayout;

    BuMenFlowBO buMenFlowBO;

    @Override
    protected int getLayout() {
        return R.layout.act_bumen_flow;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("部门选择");

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
        flowLayout.setAdapter(adapter);
        flowLayout.setOnTagClickListener((view, position, parent) -> {
            adapter.setSelectPosition(position);
            adapter.notifyDataChanged();
            buMenFlowBO = buMenFlowBOS.get(position);
            return true;
        });
    }


    class FlowAdapter extends TagAdapter<BuMenFlowBO> {

        int selectPosition = 0;

        void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
        }


        FlowAdapter(List<BuMenFlowBO> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, BuMenFlowBO buMenFlowBO) {
            View view = getLayoutInflater().inflate(R.layout.item_bumen_flow,
                    null);
            TextView bumenText = view.findViewById(R.id.flow_text);
            bumenText.setText(buMenFlowBO.getName());
            if (position == selectPosition) {
                bumenText.setTextColor(ContextCompat.getColor(BumenActivity.this, R.color.blue_color));
                bumenText.setBackgroundResource(R.drawable.menu_item_select);
            } else {
                bumenText.setTextColor(ContextCompat.getColor(BumenActivity.this, R.color.tab_txt_color));
                bumenText.setBackgroundResource(R.drawable.menu_item_bg);
            }
            return view;
        }
    }

}
