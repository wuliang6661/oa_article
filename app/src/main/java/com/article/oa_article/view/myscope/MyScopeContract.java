package com.article.oa_article.view.myscope;

import com.article.oa_article.bean.ScopeBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MyScopeContract {
    interface View extends BaseRequestView {

        void getScopeList(List<ScopeBO> s);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
