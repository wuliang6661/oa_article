package com.wul.oa_article.view.mobanmanager;

import com.wul.oa_article.bean.MuBanTaskBO;
import com.wul.oa_article.bean.TempleteBO;
import com.wul.oa_article.mvp.BasePresenter;
import com.wul.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MobanManagerContract {
    interface View extends BaseRequestView {

        void getMoBan(List<TempleteBO> templeteBOS);

        void makeMuBanSoress(List<MuBanTaskBO> muBanTaskBOS);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
