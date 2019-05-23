package com.article.oa_article.view.moveaddperson;

import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MoveAddPersonContract {
    interface View extends BaseRequestView {

        void addUserSuress();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
