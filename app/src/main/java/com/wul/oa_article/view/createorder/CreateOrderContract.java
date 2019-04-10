package com.wul.oa_article.view.createorder;

import com.wul.oa_article.mvp.BasePresenter;
import com.wul.oa_article.mvp.BaseRequestView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateOrderContract {
    interface View extends BaseRequestView {


        void updateSourss(String name, String imageUrl);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
