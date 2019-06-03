package com.article.oa_article.view.newlycomplan;

import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NewlyComplanContract {
    interface View extends BaseRequestView {

        void addSourss();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
