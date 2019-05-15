package com.article.oa_article.module.complanyzizhi;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseFragment;

/**
 * MVPPlugin
 * 公司资质详情
 */

public class ComplanyZizhiFragment extends MVPBaseFragment<ComplanyZizhiContract.View, ComplanyZizhiPresenter>
        implements ComplanyZizhiContract.View {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fra_complany_zizhi, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
