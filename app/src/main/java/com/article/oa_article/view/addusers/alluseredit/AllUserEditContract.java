package com.article.oa_article.view.addusers.alluseredit;

import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AllUserEditContract {
    interface View extends BaseRequestView {

        void sourss();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
