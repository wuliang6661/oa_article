package com.article.oa_article.view.newlycomplan;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.article.oa_article.R;
import com.article.oa_article.bean.request.AddComplanRequest;
import com.article.oa_article.module.complanmsgedit.ComplanMsgEditFragment;
import com.article.oa_article.module.complanshiliedit.ComplanShiliEditFragment;
import com.article.oa_article.module.complanziyuanedit.ComplanZiyuanEditFragment;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.blankj.utilcode.util.FragmentUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 新增企业
 */

public class NewlyComplanActivity extends MVPBaseActivity<NewlyComplanContract.View, NewlyComplanPresenter>
        implements NewlyComplanContract.View {

    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.kehu_order_check)
    CheckBox kehuOrderCheck;
    @BindView(R.id.complan_details)
    FrameLayout complanDetails;
    @BindView(R.id.gongsi_ziyuan_check)
    CheckBox gongsiZiyuanCheck;
    @BindView(R.id.complan_zizhi)
    FrameLayout complanZizhi;
    @BindView(R.id.shili_check)
    CheckBox shiliCheck;
    @BindView(R.id.complan_shili)
    FrameLayout complanShili;

    ComplanMsgEditFragment fragment;
    ComplanZiyuanEditFragment fragment1;
    ComplanShiliEditFragment fragment2;

    @Override
    protected int getLayout() {
        return R.layout.act_newly_complan;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("新增企业");

        fragment = new ComplanMsgEditFragment();
        fragment1 = new ComplanZiyuanEditFragment();
        fragment2 = new ComplanShiliEditFragment();

        FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.complan_details);
        FragmentUtils.replace(getSupportFragmentManager(), fragment2, R.id.complan_shili);
        FragmentUtils.replace(getSupportFragmentManager(), fragment1, R.id.complan_zizhi);
    }


    @OnClick({R.id.complan_bar, R.id.complan_ziyuan_bar, R.id.shili_bar})
    public void barClick(View view) {
        switch (view.getId()) {
            case R.id.complan_bar:
                if (complanDetails.getVisibility() == View.VISIBLE) {
                    complanDetails.setVisibility(View.GONE);
                    kehuOrderCheck.setChecked(true);
                } else {
                    complanDetails.setVisibility(View.VISIBLE);
                    kehuOrderCheck.setChecked(false);
                }
                break;
            case R.id.complan_ziyuan_bar:
                if (complanZizhi.getVisibility() == View.VISIBLE) {
                    complanZizhi.setVisibility(View.GONE);
                    gongsiZiyuanCheck.setChecked(true);
                } else {
                    complanZizhi.setVisibility(View.VISIBLE);
                    gongsiZiyuanCheck.setChecked(false);
                }
                break;
            case R.id.shili_bar:
                if (complanShili.getVisibility() == View.VISIBLE) {
                    complanShili.setVisibility(View.GONE);
                    shiliCheck.setChecked(true);
                } else {
                    complanShili.setVisibility(View.VISIBLE);
                    shiliCheck.setChecked(false);
                }
                break;
        }
    }


    @OnClick(R.id.next_button)
    public void commit() {
        AddComplanRequest request = new AddComplanRequest();
        if (fragment.isCommit()) {
            request = fragment.getData(request);
            if (fragment1.isCommit()) {
                request = fragment1.getData(request);
                if (fragment2.getData() != null) {
                    request.setCompanyQualifications(fragment2.getData());
                    if (fragment2.getHonorDatas() != null) {
                        request.setCompanyHonors(fragment2.getHonorDatas());
                        mPresenter.addComplan(request);
                    }
                }
            }
        }
    }

    @Override
    public void addSourss() {
        showToast("新增企业成功，已提交审核！");
        finish();
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }
}
