package com.wul.oa_article.module.task_accept;

import android.content.Context;

import com.wul.oa_article.mvp.BasePresenter;
import com.wul.oa_article.mvp.BaseRequestView;
import com.wul.oa_article.mvp.BaseView;

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
