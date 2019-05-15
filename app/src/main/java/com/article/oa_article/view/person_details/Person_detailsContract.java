package com.article.oa_article.view.person_details;

import com.article.oa_article.bean.UserInInfoBo;
import com.article.oa_article.bean.UserOutInfo;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;
import com.article.oa_article.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Person_detailsContract {
    interface View extends BaseRequestView {

        void getUserInInfo(UserInInfoBo inInfoBo);

        void getUserOutInfo(UserOutInfo info);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
