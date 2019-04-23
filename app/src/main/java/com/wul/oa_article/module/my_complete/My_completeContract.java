package com.wul.oa_article.module.my_complete;

import com.wul.oa_article.mvp.BasePresenter;
import com.wul.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class My_completeContract {
    interface View extends BaseRequestView {

        void updateNumSuress();

        void commitSuress();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
