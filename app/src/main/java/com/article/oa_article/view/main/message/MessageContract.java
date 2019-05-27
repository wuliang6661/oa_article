package com.article.oa_article.view.main.message;

import com.article.oa_article.bean.MsgBO;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MessageContract {
    interface View extends BaseRequestView {

        void getNoReadCount(Integer num);

        void getMsgList(List<MsgBO> msgBOS);

        void readSuress();
    }

    interface Presenter extends BasePresenter<View> {

    }
}
