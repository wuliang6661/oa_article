package com.wul.oa_article.view.main;

import com.wul.oa_article.mvp.BasePresenter;
import com.wul.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainContract {
    interface View extends BaseRequestView {

    }

    interface Presenter extends BasePresenter<View> {

    }
}
