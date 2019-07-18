package com.article.oa_article.module.task_allot;

import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Task_allotContract {
    interface View extends BaseRequestView {

        void taskSourss();

        void shunyanSourss();

        void cancleSuress(int position, boolean isDelete);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
