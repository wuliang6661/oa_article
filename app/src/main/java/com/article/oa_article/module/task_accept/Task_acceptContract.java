package com.article.oa_article.module.task_accept;

import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Task_acceptContract {
    interface View extends BaseRequestView {

        void sourss(int type);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
