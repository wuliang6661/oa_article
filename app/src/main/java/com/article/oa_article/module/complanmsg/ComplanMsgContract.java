package com.article.oa_article.module.complanmsg;

import com.article.oa_article.bean.ComplanBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ComplanMsgContract {
    interface View extends BaseRequestView {

        void getComplanInfo(ComplanBO complanBO);
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
