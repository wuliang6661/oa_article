package com.article.oa_article.view.alreadyscope;

import com.article.oa_article.bean.AlreadyScopeBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AlreadyScopeContract {
    interface View extends BaseRequestView {

        void getAlreadyScope(List<AlreadyScopeBO> alreadyScopeBOS);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
