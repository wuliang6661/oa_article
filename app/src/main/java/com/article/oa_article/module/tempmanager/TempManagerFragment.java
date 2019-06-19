package com.article.oa_article.module.tempmanager;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.article.oa_article.R;
import com.article.oa_article.bean.CountNumBO;
import com.article.oa_article.bean.ShareBo;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.view.bumenmanager.BumenManagerActivity;
import com.article.oa_article.view.personmanager.PersonManagerActivity;
import com.article.oa_article.wxapi.WxShareUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TempManagerFragment extends MVPBaseFragment<TempManagerContract.View, TempManagerPresenter>
        implements TempManagerContract.View {

    @BindView(R.id.temp_num)
    TextView tempNum;
    @BindView(R.id.person_num)
    TextView personNum;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.person_name_layout)
    LinearLayout personNameLayout;
    @BindView(R.id.temp_manager_layout)
    LinearLayout tempManagerLayout;
    @BindView(R.id.friends_manager)
    LinearLayout friendsManager;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_temp_manager, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.getCount();
    }


    @OnClick({R.id.temp_manager_layout, R.id.person_name_layout, R.id.friends_manager})
    public void layoutClick(View view) {
        switch (view.getId()) {
            case R.id.temp_manager_layout:
                gotoActivity(BumenManagerActivity.class, false);
                break;
            case R.id.person_name_layout:
                gotoActivity(PersonManagerActivity.class, false);
                break;
            case R.id.friends_manager:
//                PopPersonAdd addPop = new PopPersonAdd(getActivity());
//                addPop.setListener(new PopPersonAdd.onCommitListener() {
//                    @Override
//                    public void shoudongAdd() {
//                        Bundle bundle = new Bundle();
//                        bundle.putBoolean("isNeiBu", true);
//                        gotoActivity(MoveAddPersonActivity.class, bundle, false);
//                    }
//
//                    @Override
//                    public void piliangAdd() {
//                        Bundle bundle = new Bundle();
//                        bundle.putBoolean("isNeiBu", true);
//                        gotoActivity(AddUsersActivity.class, bundle, false);
//                    }
//                });
//                addPop.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                mPresenter.getShareMsg();
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRequestError(String msg) {
        showToast(msg);
    }

    @Override
    public void getCount(CountNumBO countNumBO) {
        tempNum.setText(countNumBO.getDeparts() + "");
        personNum.setText(countNumBO.getCompanyUsers() + "");
        orderNum.setText(countNumBO.getOrders() + "");
    }

    @Override
    public void getShare(ShareBo shareBo) {
        WxShareUtils.get().wechatShare(getActivity(), 0, shareBo);
    }
}
