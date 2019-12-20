package com.article.oa_article.module.systemsetting;


import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.article.oa_article.R;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.ShareBo;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.view.login.LoginActivity;
import com.article.oa_article.view.optionsfankui.OptionsFankuiActivity;
import com.article.oa_article.view.splash.guide.GuiDeAct1;
import com.article.oa_article.widget.AlertDialog;
import com.article.oa_article.widget.PopShare;
import com.article.oa_article.wxapi.WxShareUtils;
import com.m7.imkfsdk.KfStartHelper;
import com.m7.imkfsdk.utils.PermissionUtils;
import com.moor.imkf.model.entity.CardInfo;

import java.net.URLEncoder;

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

    KfStartHelper helper;

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

        /**
         * 文件写入权限 （初始化需要写入文件，点击在线客服按钮之前需打开文件写入权限）
         * 读取设备 ID 权限 （初始化需要获取用户的设备 ID）
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionUtils.hasAlwaysDeniedPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                PermissionUtils.requestPermissions(getActivity(), 0x11, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                    }

                    @Override
                    public void onPermissionDenied(String[] deniedPermissions) {
                        Toast.makeText(getActivity(), com.m7.imkfsdk.R.string.notpermession, Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                            }
                        }, 2000);
                    }
                });
            }
        }
        /**
         * 第一步：初始化help 文件
         */
        helper = new KfStartHelper(getActivity());
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
                            JPushInterface.cleanTags(getActivity(), 1);
                            MyApplication.spUtils.remove("password");
                            MyApplication.spUtils.remove("loginCode");
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


    @OnClick(R.id.xianshang_zixun)
    public void xianshangzixun() {
        /**
         * 第二步
         * 设置参数
         * 初始化sdk方法，必须先调用该方法进行初始化后才能使用IM相关功能
         * @param accessId       接入id（需后台配置获取）
         * @param userName       用户名
         * @param userId         用户id
         */
        String s = "https://wap.boosoo.com.cn/bobishop/goodsdetail?id=10160&mid=36819";
        CardInfo ci = new CardInfo("http://seopic.699pic.com/photo/40023/0579.jpg_wh1200.jpg", "我是一个标题当初读书", "我是name当初读书。", "价格 1000-9999", "https://www.baidu.com");
        String icon = MyApplication.userBo.getImage();
        String title = "美式北欧吊灯家居灯卧室客厅书房餐厅灯D1-31008-12头";
        String content = "8头/φ520*H350/96W 天下灯仓包装 黑色";
        String rigth3 = " ¥548.00";
        try {
            ci = new CardInfo(URLEncoder.encode(icon, "utf-8"), URLEncoder.encode(title, "utf-8"), URLEncoder.encode("", "utf-8"), URLEncoder.encode("", "utf-8"),
                    URLEncoder.encode(s, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        helper.setCard(ci);
        helper.initSdkChat("97207a70-1a52-11ea-889d-798f29dfa486", "测试", "123456789");//腾讯云正式
    }


    /**
     * 权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            PermissionUtils.onRequestPermissionsResult(getActivity(), 0x11, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, grantResults);
        }
    }


    @OnClick(R.id.friends_manager)
    public void layoutClick(View view) {
        PopShare share = new PopShare(getActivity());
        share.setListener(new PopShare.onCommitListener() {
            @Override
            public void shareFriend() {
                mPresenter.getShareMsg(0);
            }

            @Override
            public void shareMenmens() {
                mPresenter.getShareMsg(1);
            }
        });
        share.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void getShare(int flag, ShareBo shareBo) {
        WxShareUtils.get().wechatShare(getActivity(), flag, shareBo);
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }
}
