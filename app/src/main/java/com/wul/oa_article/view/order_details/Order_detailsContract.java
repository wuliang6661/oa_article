package com.wul.oa_article.view.order_details;

import com.wul.oa_article.bean.OrderInfoBo;
import com.wul.oa_article.bean.PenPaiTaskBO;
import com.wul.oa_article.bean.TaskDetails;
import com.wul.oa_article.mvp.BasePresenter;
import com.wul.oa_article.mvp.BaseRequestView;

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
    }

    interface Presenter extends BasePresenter<View> {

    }
}
