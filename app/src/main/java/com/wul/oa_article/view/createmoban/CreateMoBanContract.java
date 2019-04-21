package com.wul.oa_article.view.createmoban;

import com.wul.oa_article.bean.TempleteInfoBo;
import com.wul.oa_article.mvp.BasePresenter;
import com.wul.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateMoBanContract {
    interface View extends BaseRequestView {

        void getMoBanInfo(TempleteInfoBo infoBo);

        void onSuress();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
