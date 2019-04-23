package com.wul.oa_article.view.main.home.accepted;

import com.wul.oa_article.bean.ClientOrderBo;
import com.wul.oa_article.mvp.BasePresenter;
import com.wul.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AcceptedContract {
    interface View extends BaseRequestView {

        void getClientInfo(ClientOrderBo clientOrderBo);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
