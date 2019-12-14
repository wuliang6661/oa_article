package com.article.oa_article.module.systemsetting;

import com.article.oa_article.bean.ShareBo;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SystemSettingContract {
    interface View extends BaseRequestView {

        void getShare(int flag, ShareBo shareBo);

    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
