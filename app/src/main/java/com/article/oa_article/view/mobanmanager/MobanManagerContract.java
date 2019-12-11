package com.article.oa_article.view.mobanmanager;

import com.article.oa_article.bean.MuBanTaskBO;
import com.article.oa_article.bean.TempleteBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MobanManagerContract {
    interface View extends BaseRequestView {

        void getMoBan(List<TempleteBO> templeteBOS);

        void makeMuBanSoress(List<MuBanTaskBO> muBanTaskBOS);

        void deleteSourss();

        void moveSoruss();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
