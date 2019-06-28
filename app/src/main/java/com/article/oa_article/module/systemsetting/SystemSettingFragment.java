package com.article.oa_article.module.systemsetting;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.util.AppManager;
import com.article.oa_article.view.login.LoginActivity;
import com.article.oa_article.view.optionsfankui.OptionsFankuiActivity;
import com.article.oa_article.view.splash.guide.GuiDeAct1;
import com.article.oa_article.widget.AlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SystemSettingFragment extends MVPBaseFragment<SystemSettingContract.View, SystemSettingPresenter>
        implements SystemSettingContract.View {


    @BindView(R.id.xianshang_zixun)
    LinearLayout xianshangZixun;
    @BindView(R.id.options_fankui)
    LinearLayout optionsFankui;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_system_setting, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.options_fankui, R.id.loginout})
    public void barClick(View view) {
        switch (view.getId()) {
            case R.id.options_fankui:
                gotoActivity(OptionsFankuiActivity.class, false);
                break;
            case R.id.loginout:
                new AlertDialog(getActivity()).builder().setGone().setMsg("是否确定退出登录？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", v -> {
                            JPushInterface.deleteAlias(getActivity(), 1);
                            AppManager.getAppManager().finishAllActivity();
                            gotoActivity(LoginActivity.class, true);
                        }).show();
                break;
        }
    }


    @OnClick(R.id.xinshou_layout)
    public void xinshou() {
        MyApplication.isSplash = false;
        gotoActivity(GuiDeAct1.class, false);
    }

}
