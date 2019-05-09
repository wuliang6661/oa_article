package com.article.oa_article.view.main.message;

import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MessageContract {
    interface View extends BaseRequestView {

        void getNoReadCount(Integer num);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
