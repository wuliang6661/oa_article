package com.article.oa_article.module.complanydetails;


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
 * 公司详情展示
 */

public class ComplanyDetailsFragment extends MVPBaseFragment<ComplanyDetailsContract.View, ComplanyDetailsPresenter>
        implements ComplanyDetailsContract.View {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fra_complany_details, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
