package com.article.oa_article.view.main.mine;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.UserBo;
import com.article.oa_article.module.scopecenter.ScopeCenterFragment;
import com.article.oa_article.module.taskcenter.TaskCenterFragment;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.view.setting.SettingActivity;

import java.util.ArrayList;
import java.util.List;

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

    private String[] tabs = new String[]{"产能分析", "评价中心", "任务数据", "企业认证", "管理团队"};

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
        mPresenter.getUserInfo();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText(tabs[0]));
        tabLayout.addTab(tabLayout.newTab().setText(tabs[1]));
        tabLayout.addTab(tabLayout.newTab().setText(tabs[2]));
        tabLayout.addTab(tabLayout.newTab().setText(tabs[3]));
        tabLayout.addTab(tabLayout.newTab().setText(tabs[4]));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragment());
        fragments.add(new ScopeCenterFragment());
        fragments.add(new TaskCenterFragment());
        fragments.add(new Fragment());
        fragments.add(new Fragment());
        viewPager.setAdapter(new PagerAdapter(getFragmentManager(), fragments, tabs));
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(5);
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

    @Override
    public void getUser(UserBo userBo) {
        personName.setText(userBo.getName());
        personPhone.setText(userBo.getPhone());
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }
}