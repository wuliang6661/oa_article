package com.article.oa_article.view.person_details;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.base.GlideApp;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.bean.UserInInfoBo;
import com.article.oa_article.bean.UserOutInfo;
import com.article.oa_article.bean.request.UserInInfoRequest;
import com.article.oa_article.bean.request.UserOutRequest;
import com.article.oa_article.module.chatline.ChatLineFragment;
import com.article.oa_article.module.complanydetails.ComplanyDetailsFragment;
import com.article.oa_article.module.complanyshili.ComplanyshiliFragment;
import com.article.oa_article.module.complanyzizhi.ComplanyZizhiFragment;
import com.article.oa_article.mvp.MVPBaseActivity;
import com.blankj.utilcode.util.FragmentUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 名片
 */

public class Person_detailsActivity extends MVPBaseActivity<Person_detailsContract.View, Person_detailsPresenter>
        implements Person_detailsContract.View {


    @BindView(R.id.person_img)
    RoundedImageView personImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_lable)
    TextView userLable;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.complan_name)
    TextView complanName;
    @BindView(R.id.wanchenglv)
    TextView wanchenglv;
    @BindView(R.id.pingfen)
    TextView pingfen;
    @BindView(R.id.yuqilv)
    TextView yuqilv;
    @BindView(R.id.kehu_order_check)
    CheckBox kehuOrderCheck;
    @BindView(R.id.complan_bar)
    LinearLayout complanBar;
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
    @BindView(R.id.chanlian)
    FrameLayout chanlian;
    @BindView(R.id.pingfen_layout)
    LinearLayout pingfenLayout;

    private boolean isNeiBu = false;   //是否是内部员工
    private int userId;    //人员Id
    private String departName;   //部门名称


    @Override
    protected int getLayout() {
        return R.layout.act_person_details;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("");

        isNeiBu = getIntent().getExtras().getBoolean("isNeiBu");
        userId = getIntent().getExtras().getInt("personId");
        departName = getIntent().getExtras().getString("departName");

        initView();
        if (isNeiBu) {
            UserInInfoRequest request = new UserInInfoRequest();
            request.setDepartName(departName);
            request.setUserId(userId);
            mPresenter.getInUserInfo(request);
        } else {
            UserOutRequest request = new UserOutRequest();
            request.setUserId(userId);
            mPresenter.getOutUserInfo(request);
            mPresenter.getComplanMsg();
        }
    }


    /**
     * 初始化布局
     */
    private void initView() {
        if (isNeiBu) {
            complanBar.setVisibility(View.GONE);
            complanDetails.setVisibility(View.GONE);
            complanZiyuanBar.setVisibility(View.GONE);
            complanZizhi.setVisibility(View.GONE);
            shiliBar.setVisibility(View.GONE);
            complanShili.setVisibility(View.GONE);
            pingfenLayout.setVisibility(View.GONE);
        } else {
            complanBar.setVisibility(View.VISIBLE);
            complanDetails.setVisibility(View.VISIBLE);
            complanZiyuanBar.setVisibility(View.VISIBLE);
            complanZizhi.setVisibility(View.VISIBLE);
            shiliBar.setVisibility(View.VISIBLE);
            complanShili.setVisibility(View.VISIBLE);
            pingfenLayout.setVisibility(View.VISIBLE);
        }
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


    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getUserInInfo(UserInInfoBo inInfoBo) {
        GlideApp.with(this).load(inInfoBo.getImage()).error(R.drawable.person_img_defailt)
                .placeholder(R.drawable.person_img_defailt).into(personImg);
        userName.setText(inInfoBo.getNickName());
        userPhone.setText(inInfoBo.getPhone());
        complanName.setText(inInfoBo.getCompanyName());
        userLable.setText(inInfoBo.getDepartName());
        wanchenglv.setText(inInfoBo.getCompleteRate());
        yuqilv.setText(inInfoBo.getOverdueRate());

        ChatLineFragment fragment = ChatLineFragment.getInstance(1);
        fragment.setUserBo(userId, Integer.parseInt(MyApplication.getCommonId()));
        FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.chanlian);
    }

    @Override
    public void getUserOutInfo(UserOutInfo info) {
        GlideApp.with(this).load(info.getImage()).error(R.drawable.person_img_defailt)
                .placeholder(R.drawable.person_img_defailt).into(personImg);
        userName.setText(info.getNickName());
        userPhone.setText(info.getPhone());
        complanName.setText(info.getCompanys().get(0).getCompanyName());
//        userLable.setText(inInfoBo.getDepartName());
        wanchenglv.setText(info.getCompanys().get(0).getCompleteRate());
        pingfen.setText(info.getScore() + "");
        yuqilv.setText(info.getCompanys().get(0).getOverdueRate());

        ChatLineFragment fragment = ChatLineFragment.getInstance(1);
        fragment.setUserBo(userId, info.getCompanys().get(0).getCompanyId());
        FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.chanlian);
    }

    @Override
    public void getComplanInfo(ComplanBO complanBO) {
        ComplanyDetailsFragment fragment = new ComplanyDetailsFragment();
        ComplanyshiliFragment shiliFragment = new ComplanyshiliFragment();
        ComplanyZizhiFragment zizhiFragment = new ComplanyZizhiFragment();

        FragmentUtils.replace(getSupportFragmentManager(), fragment, R.id.complan_details);
        FragmentUtils.replace(getSupportFragmentManager(), shiliFragment, R.id.complan_shili);
        FragmentUtils.replace(getSupportFragmentManager(), zizhiFragment, R.id.complan_zizhi);

        fragment.setComplanBo(complanBO);
        zizhiFragment.setComplanBo(complanBO);
        shiliFragment.setComplanBo(complanBO);
    }
}
