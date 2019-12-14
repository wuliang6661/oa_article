package com.article.oa_article.view.main.personlist;

import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.bean.ShareBo;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonListContract {
    interface View extends BaseRequestView {

        void getPersonListByNeiBu(List<BumenBO> bumenBOS);

        void getPersonListByWaiBu(List<BumenBO> bumenBOS);

        void updateDeats();

        void getShare(int flag, ShareBo shareBo);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
