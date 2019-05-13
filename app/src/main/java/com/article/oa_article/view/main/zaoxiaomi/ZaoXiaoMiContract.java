package com.article.oa_article.view.main.zaoxiaomi;

import android.content.Context;

import com.article.oa_article.bean.DateShemeBO;
import com.article.oa_article.bean.DateTaskBo;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;
import com.article.oa_article.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ZaoXiaoMiContract {
    interface View extends BaseRequestView {

        void getShameDate(List<DateShemeBO> shemeBOS);

        void getTaskByYuqi(List<DateTaskBo> dateTaskBos);

        void getTaskByToday(List<DateTaskBo> dateTaskBos);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
