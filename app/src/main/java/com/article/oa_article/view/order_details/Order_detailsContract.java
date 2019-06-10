package com.article.oa_article.view.order_details;

import com.article.oa_article.bean.OrderInfoBo;
import com.article.oa_article.bean.PenPaiTaskBO;
import com.article.oa_article.bean.TaskDetails;
import com.article.oa_article.mvp.BasePresenter;
import com.article.oa_article.mvp.BaseRequestView;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Order_detailsContract {
    interface View extends BaseRequestView {

        void getOrderInfo(OrderInfoBo orderInfoBo);

        void getTaskList(List<PenPaiTaskBO> taskBOList);

        void getTaskInfo(TaskDetails details);

        void taskCanEdit(boolean isCanEdit);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
