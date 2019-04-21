package com.wul.oa_article.view.main.personlist;

import com.wul.oa_article.bean.BumenBO;
import com.wul.oa_article.mvp.BasePresenter;
import com.wul.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonListContract {
    interface View extends BaseRequestView {

        void getPersonListByNeiBu(List<BumenBO> bumenBOS);

        void getPersonListByWaiBu(List<BumenBO> bumenBOS);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
