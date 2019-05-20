package com.article.oa_article.view.bumenmanager;

import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BumenManagerContract {
    interface View extends BaseRequestView {

        void getBumenList(List<BuMenFlowBO> bumens);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
