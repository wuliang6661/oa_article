package com.article.oa_article.module.complanziyuanedit;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.widget.EditMsgText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 编辑公司资源
 */

public class ComplanZiyuanEditFragment extends MVPBaseFragment<ComplanZiyuanEditContract.View,
        ComplanZiyuanEditPresenter> implements ComplanZiyuanEditContract.View {


    @BindView(R.id.complan_guanli_num)
    EditMsgText complanGuanliNum;
    @BindView(R.id.complan_jishu_num)
    EditMsgText complanJishuNum;
    @BindView(R.id.complan_pugong_num)
    EditMsgText complanPugongNum;
    @BindView(R.id.shebei_recycle)
    RecyclerView shebeiRecycle;
    @BindView(R.id.add_pinglei)
    LinearLayout addPinglei;
    @BindView(R.id.complan_changfang_mianji)
    EditMsgText complanChangfangMianji;
    @BindView(R.id.complan_changfang_xingzhi)
    EditMsgText complanChangfangXingzhi;
    @BindView(R.id.image_recycle)
    RecyclerView imageRecycle;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_complan_ziyuan, null);
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
