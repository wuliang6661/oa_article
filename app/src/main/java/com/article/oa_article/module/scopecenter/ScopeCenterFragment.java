package com.article.oa_article.module.scopecenter;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.article.oa_article.R;
import com.article.oa_article.mvp.MVPBaseFragment;
import com.article.oa_article.view.myscope.MyScopeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 评价中心
 */

public class ScopeCenterFragment extends MVPBaseFragment<ScopeCenterContract.View, ScopeCenterPresenter>
        implements ScopeCenterContract.View {


    @BindView(R.id.shoudao_scope)
    LinearLayout shoudaoScope;
    @BindView(R.id.yi_pingjia)
    LinearLayout yiPingjia;
    @BindView(R.id.dai_pingjia)
    LinearLayout daiPingjia;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_scope, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @OnClick({R.id.shoudao_scope, R.id.yi_pingjia, R.id.dai_pingjia})
    public void pingjiaClick(View view) {
        switch (view.getId()) {
            case R.id.shoudao_scope:
                gotoActivity(MyScopeActivity.class, false);
                break;
            case R.id.yi_pingjia:

                break;
            case R.id.dai_pingjia:

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
