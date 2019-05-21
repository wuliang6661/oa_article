package com.article.oa_article.module.complanmsgedit;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.widget.EditMsgText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 修改公司信息
 */

public class ComplanMsgEditFragment extends MVPBaseFragment<ComplanMsgEditContract.View, ComplanMsgEditPresenter>
        implements ComplanMsgEditContract.View {

    @BindView(R.id.complan_name)
    EditMsgText complanName;
    @BindView(R.id.complan_jiancheng)
    EditMsgText complanJiancheng;
    @BindView(R.id.complan_address)
    EditMsgText complanAddress;
    @BindView(R.id.complan_phone)
    EditMsgText complanPhone;
    @BindView(R.id.complan_email)
    EditMsgText complanEmail;
    @BindView(R.id.card_fanmian)
    ImageView cardFanmian;
    @BindView(R.id.delete_fanmian)
    ImageView deleteFanmian;
    @BindView(R.id.card_zhengmian)
    ImageView cardZhengmian;
    @BindView(R.id.delete_img)
    ImageView deleteImg;
    @BindView(R.id.zhizhao)
    ImageView zhizhao;
    @BindView(R.id.delete_zhizhao)
    ImageView deleteZhizhao;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_edit_complan_detail, null);
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
}
