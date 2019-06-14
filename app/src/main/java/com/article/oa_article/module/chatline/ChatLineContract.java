package com.article.oa_article.module.chatline;

import com.article.oa_article.bean.ChartBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ChatLineContract {
    interface View extends BaseRequestView {

        void getChatLine(List<ChartBO> chartBOS);

        void addOutSoress();

        void getBiaoData(List<ChartBO> chartBOS);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
