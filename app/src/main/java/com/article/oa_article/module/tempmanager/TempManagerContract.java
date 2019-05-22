package com.article.oa_article.module.tempmanager;

import com.article.oa_article.bean.CountNumBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class TempManagerContract {
    interface View extends BaseRequestView {

        void getCount(CountNumBO countNumBO);
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
