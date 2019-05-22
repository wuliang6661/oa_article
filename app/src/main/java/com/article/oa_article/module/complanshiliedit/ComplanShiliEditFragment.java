package com.article.oa_article.module.complanshiliedit;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.article.oa_article.R;
import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.mvp.MVPBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 公司实力编辑
 */

public class ComplanShiliEditFragment extends MVPBaseFragment<ComplanShiliEditContract.View,
        ComplanShiliEditPresenter> implements ComplanShiliEditContract.View {


    @BindView(R.id.zizhi_recycle)
    RecyclerView zizhiRecycle;
    @BindView(R.id.rongyu_recycle)
    RecyclerView rongyuRecycle;
    Unbinder unbinder;


    ComplanBO complanBo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_complany_shili, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        zizhiRecycle.setLayoutManager(manager);

        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rongyuRecycle.setLayoutManager(manager1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void setComplanBo(ComplanBO complanBo) {
        this.complanBo = complanBo;
        new Handler().post(this::setUIMsg);
    }


    private void setUIMsg() {


    }


}
