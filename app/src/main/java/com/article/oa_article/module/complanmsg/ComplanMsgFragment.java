package com.article.oa_article.module.complanmsg;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.bean.event.UpdateComplanEvent;
import com.article.oa_article.module.complanmsgedit.ComplanMsgEditFragment;
import com.article.oa_article.module.complanydetails.ComplanyDetailsFragment;
import com.article.oa_article.module.complanyshili.ComplanyshiliFragment;
import com.article.oa_article.module.complanyzizhi.ComplanyZizhiFragment;
import com.article.oa_article.module.complanziyuanedit.ComplanZiyuanEditFragment;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.blankj.utilcode.util.FragmentUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 名片企业展示
 */

public class ComplanMsgFragment extends MVPBaseFragment<ComplanMsgContract.View, ComplanMsgPresenter>
        implements ComplanMsgContract.View {

    @BindView(R.id.complan_bar)
    LinearLayout complanBar;
    Unbinder unbinder;
    @BindView(R.id.kehu_order_check)
    CheckBox kehuOrderCheck;
    @BindView(R.id.complan_details)
    FrameLayout complanDetails;
    @BindView(R.id.gongsi_ziyuan_check)
    CheckBox gongsiZiyuanCheck;
    @BindView(R.id.complan_ziyuan_bar)
    LinearLayout complanZiyuanBar;
    @BindView(R.id.complan_zizhi)
    FrameLayout complanZizhi;
    @BindView(R.id.shili_check)
    CheckBox shiliCheck;
    @BindView(R.id.shili_bar)
    LinearLayout shiliBar;
    @BindView(R.id.complan_shili)
    FrameLayout complanShili;
    @BindView(R.id.edit_complan)
    TextView editComplan;
    @BindView(R.id.edit_ziyuan)
    TextView editZiyuan;
    @BindView(R.id.edit_shili)
    TextView editShili;

    private int type = 0;   //默认不显示修改

    private ComplanBO complanBO;


    public static ComplanMsgFragment getInstance(int type) {
        ComplanMsgFragment fragment = new ComplanMsgFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);

        mPresenter.getComplanMsg();
        if (getArguments() != null) {
            type = getArguments().getInt("type", 0);
            if (type == 1) {   //管理员可修改
                editComplan.setVisibility(View.VISIBLE);
                editShili.setVisibility(View.VISIBLE);
                editZiyuan.setVisibility(View.VISIBLE);
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_complan_msg, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
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

    @OnClick({R.id.edit_complan, R.id.edit_shili, R.id.edit_ziyuan})
    public void editInfos(View view) {
        switch (view.getId()) {
            case R.id.edit_complan:
                ComplanMsgEditFragment fragment = new ComplanMsgEditFragment();
                FragmentUtils.replace(getFragmentManager(), fragment, R.id.complan_details);
                fragment.setData(complanBO);
                break;
            case R.id.edit_ziyuan:
                ComplanZiyuanEditFragment fragment1 = new ComplanZiyuanEditFragment();
                FragmentUtils.replace(getFragmentManager(), fragment1, R.id.complan_details);
                fragment1.setData(complanBO);
                break;
            case R.id.edit_shili:

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void editSuress(UpdateComplanEvent event) {
        mPresenter.getComplanMsg();
    }


    @Override
    public void getComplanInfo(ComplanBO complanBO) {
        this.complanBO = complanBO;
        ComplanyDetailsFragment fragment = new ComplanyDetailsFragment();
        ComplanyshiliFragment shiliFragment = new ComplanyshiliFragment();
        ComplanyZizhiFragment zizhiFragment = new ComplanyZizhiFragment();

        FragmentUtils.replace(getFragmentManager(), fragment, R.id.complan_details);
        FragmentUtils.replace(getFragmentManager(), shiliFragment, R.id.complan_shili);
        FragmentUtils.replace(getFragmentManager(), zizhiFragment, R.id.complan_zizhi);

        fragment.setComplanBo(complanBO);
        zizhiFragment.setComplanBo(complanBO);
        shiliFragment.setComplanBo(complanBO);
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
