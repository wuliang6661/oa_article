package com.article.oa_article.view.lablecustom;

import com.article.oa_article.bean.LableBo;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LableCustomContract {
    interface View extends BaseRequestView {

        void getLable(LableBo lableBo);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
