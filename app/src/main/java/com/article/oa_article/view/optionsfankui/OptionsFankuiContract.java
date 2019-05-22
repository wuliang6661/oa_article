package com.article.oa_article.view.optionsfankui;

import android.content.Context;

import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;
import com.article.oa_article.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OptionsFankuiContract {
    interface View extends BaseRequestView {

        void sourss();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
