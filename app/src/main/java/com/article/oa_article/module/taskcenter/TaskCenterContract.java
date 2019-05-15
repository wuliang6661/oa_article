package com.article.oa_article.module.taskcenter;

import com.article.oa_article.bean.TaskCenterBo;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TaskCenterContract {
    interface View extends BaseRequestView {

        void getTaskList(List<TaskCenterBo> taskCenterBos);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
