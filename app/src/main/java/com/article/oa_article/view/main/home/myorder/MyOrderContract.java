package com.article.oa_article.view.main.home.myorder;

import com.article.oa_article.bean.TaskNumBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyOrderContract {
    interface View extends BaseRequestView {

        void getTaskNum(TaskNumBO taskNumBO);
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
