package com.article.oa_article.module.complanmanager;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.article.oa_article.mvp.MVPBaseFragment;

/**
 * MVPPlugin
 * 首页企业管理
 */

public class ComplanManagerFragment extends MVPBaseFragment<ComplanManagerContract.View, ComplanManagerPresenter>
        implements ComplanManagerContract.View {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
