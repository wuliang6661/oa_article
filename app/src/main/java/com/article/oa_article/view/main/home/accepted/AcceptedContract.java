package com.article.oa_article.view.main.home.accepted;

import com.article.oa_article.bean.ClientOrderBo;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AcceptedContract {
    interface View extends BaseRequestView {

        void getClientInfo(int taskId, ClientOrderBo clientOrderBo);

        void selectComplanSouress(int taskId, int complanId);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
