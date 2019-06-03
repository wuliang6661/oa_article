package com.article.oa_article.module.complanmsgedit;

import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ComplanMsgEditContract {
    interface View extends BaseRequestView {

        void updateSourss(String name, String imageUrl);

        void updateSourss();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
