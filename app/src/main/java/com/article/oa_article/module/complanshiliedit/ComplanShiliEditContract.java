package com.article.oa_article.module.complanshiliedit;

import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ComplanShiliEditContract {
    interface View extends BaseRequestView {

        void updateSourss(String name, String imageUrl);
        
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
