package com.article.oa_article.view.main.mine;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.GlideApp;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.LableBo;
import com.article.oa_article.bean.UserBo;
import com.article.oa_article.module.chatline.ChatLineFragment;
import com.article.oa_article.module.complanmsg.ComplanMsgFragment;
import com.article.oa_article.module.scopecenter.ScopeCenterFragment;
import com.article.oa_article.module.systemsetting.SystemSettingFragment;
import com.article.oa_article.module.taskcenter.TaskCenterFragment;
import com.article.oa_article.module.tempmanager.TempManagerFragment;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.view.newlycomplan.NewlyComplanActivity;
import com.article.oa_article.view.setting.SettingActivity;
import com.article.oa_article.widget.PopTaskMsg;
import com.blankj.utilcode.util.FragmentUtils;

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
    Unbinder unbinder;

    private String[] tabsAdmain = new String[]{"产能分析", "评价中心", "任务数据", "企业认证", "管理团队"};

    private String[] tabs = new String[]{"产能分析", "评价中心", "任务数据", "企业认证", "系统设置"};

    List<Fragment> fragments = new ArrayList<>();

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
        setListener();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getUserInfo();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        tabLayout.removeAllTabs();
        fragments.clear();
        if (MyApplication.getCommon() != null && MyApplication.getCommon().getIsAdmin() == 1) {  //管理员
            tabLayout.addTab(tabLayout.newTab().setText(tabsAdmain[0]));
            tabLayout.addTab(tabLayout.newTab().setText(tabsAdmain[1]));
            tabLayout.addTab(tabLayout.newTab().setText(tabsAdmain[2]));
            tabLayout.addTab(tabLayout.newTab().setText(tabsAdmain[3]));
            tabLayout.addTab(tabLayout.newTab().setText(tabsAdmain[4]));

            ChatLineFragment chatLineFragment = ChatLineFragment.getInstance(0);
            fragments.add(chatLineFragment);
            fragments.add(new ScopeCenterFragment());
            fragments.add(new TaskCenterFragment());
            fragments.add(ComplanMsgFragment.getInstance(1));
            fragments.add(new TempManagerFragment());
        } else {
            tabLayout.addTab(tabLayout.newTab().setText(tabs[0]));
            tabLayout.addTab(tabLayout.newTab().setText(tabs[1]));
            tabLayout.addTab(tabLayout.newTab().setText(tabs[2]));
            tabLayout.addTab(tabLayout.newTab().setText(tabs[3]));
            tabLayout.addTab(tabLayout.newTab().setText(tabs[4]));

            ChatLineFragment chatLineFragment = ChatLineFragment.getInstance(0);
            fragments.add(chatLineFragment);
            fragments.add(new ScopeCenterFragment());
            fragments.add(new TaskCenterFragment());
            fragments.add(new ComplanMsgFragment());
            fragments.add(new SystemSettingFragment());
        }
        if (MyApplication.getCommon() == null) {
            complanName.setText("暂无企业");
        } else {
            complanName.setText(MyApplication.getCommon().getCompanyName());
        }
        FragmentUtils.replace(getFragmentManager(), fragments.get(0), R.id.mine_fragment);
    }


    private void setListener() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentUtils.replace(getFragmentManager(), fragments.get(tab.getPosition()), R.id.mine_fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @OnClick(R.id.person_layout)
    public void goSetting() {
        gotoActivity(SettingActivity.class, false);
    }

    /**
     * 切换公司
     */
    @OnClick(R.id.title_layout)
    public void switchComplan() {
        PopSwitchComplan popSwitchComplan = new PopSwitchComplan(getActivity());
        popSwitchComplan.setListener(new PopSwitchComplan.OnSelectComplan() {
            @Override
            public void selectComplan(int position) {
                MyApplication.selectComplan = position;
                initView();
                mPresenter.getUserInfo();
            }

            @Override
            public void addComplan() {
                popSwitchComplan.dismiss();
                PopTaskMsg popTaskMsg = new PopTaskMsg(getActivity(), "新增企业", "企业名称", "请输入企业名称");
                popTaskMsg.setListener(new PopTaskMsg.onCommitListener() {
                    @Override
                    public void commit(String text) {
                        gotoActivity(NewlyComplanActivity.class, false);
                    }

                    @Override
                    public void update(String text, LableBo.CustomLabelsBean customLabelsBean) {

                    }
                });
                popTaskMsg.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            }
        });
        popSwitchComplan.setOnDismissListener(() -> {
            complanCheck.setChecked(false);
            popSwitchComplan.backgroundAlpha(1f);
        });
        popSwitchComplan.showPop(complanName);
        complanCheck.setChecked(true);
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
        GlideApp.with(getActivity()).load(userBo.getImage()).error(R.drawable.person_img_defailt)
                .placeholder(R.drawable.person_img_defailt).into(personImg);
    }

    @Override
    public void addComplanSuress() {
        showToast("新建企业成功！");
        mPresenter.getUserInfo();
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }
}
