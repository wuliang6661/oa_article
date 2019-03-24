package com.wul.oa_article.view.main.home.myorder;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wul.oa_article.mvp.MVPBaseFragment;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyOrderFragment extends MVPBaseFragment<MyOrderContract.View, MyOrderPresenter>
        implements MyOrderContract.View {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
