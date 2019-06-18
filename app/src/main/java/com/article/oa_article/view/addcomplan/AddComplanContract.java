package com.article.oa_article.view.addcomplan;

import com.article.oa_article.bean.ApplyComplanBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddComplanContract {
    interface View extends BaseRequestView {

        void getComplanList(List<ApplyComplanBO> complanBOS);

        void addSounrss();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
