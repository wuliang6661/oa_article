package com.article.oa_article.view.main;

import com.article.oa_article.bean.SalesBo;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainContract {
    interface View extends BaseRequestView {

        void getSales(List<SalesBo> salesBos);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
