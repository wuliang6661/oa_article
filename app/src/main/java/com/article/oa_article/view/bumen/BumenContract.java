package com.article.oa_article.view.bumen;

import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BumenContract {
    interface View extends BaseRequestView {

        void getBumenFlows(List<BuMenFlowBO> buMenFlowBOS);

        void updateDeats();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
