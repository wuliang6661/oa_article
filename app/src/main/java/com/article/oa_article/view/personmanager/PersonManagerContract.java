package com.article.oa_article.view.personmanager;

import android.content.Context;

import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;
import com.article.oa_article.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonManagerContract {
    interface View extends BaseRequestView {

        void getPersonListByNeiBu(List<BumenBO> list);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
