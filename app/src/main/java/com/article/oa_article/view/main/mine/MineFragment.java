package com.article.oa_article.view.main.mine;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.view.setting.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 我的
 */

public class MineFragment extends MVPBaseFragment<MineContract.View, MinePresenter>
        implements MineContract.View {


    @BindView(R.id.complan_name)
    TextView complanName;
    @BindView(R.id.complan_check)
    CheckBox complanCheck;
    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.person_img)
    ImageView personImg;
    @BindView(R.id.person_name)
    TextView personName;
    @BindView(R.id.person_phone)
    TextView personPhone;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_mine, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText("产能分析"));
        tabLayout.addTab(tabLayout.newTab().setText("评价中心"));
        tabLayout.addTab(tabLayout.newTab().setText("任务数据"));
        tabLayout.addTab(tabLayout.newTab().setText("企业认证"));
        tabLayout.addTab(tabLayout.newTab().setText("管理团队"));
    }


    @OnClick(R.id.person_layout)
    public void goSetting() {
        gotoActivity(SettingActivity.class, false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
