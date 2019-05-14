package com.article.oa_article.view.bumen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.bean.SalesBo;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BumenActivity extends MVPBaseActivity<BumenContract.View, BumenPresenter>
        implements BumenContract.View {

    @BindView(R.id.flow_layout)
    TagFlowLayout flowLayout;

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

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getBumenFlows(List<BuMenFlowBO> buMenFlowBOS) {
        flowLayout.setAdapter(new TagAdapter<BuMenFlowBO>(buMenFlowBOS) {
            @Override
            public View getView(FlowLayout parent, int position, BuMenFlowBO o) {
                View view = getLayoutInflater().inflate(R.layout.item_bumen_flow,
                        null);
                TextView bumenText = view.findViewById(R.id.flow_text);
                bumenText.setText(o.getName());
                return view;
            }
        });
        flowLayout.setOnTagClickListener((view, position, parent) -> {
            TextView bumenText = view.findViewById(R.id.flow_text);
            bumenText.performClick();
            return true;
        });
    }
}
