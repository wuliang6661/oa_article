package com.article.oa_article.view.main.mine;

import com.article.oa_article.bean.UserBo;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MineContract {
    interface View extends BaseRequestView {

        void getUser(UserBo userBo);
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
