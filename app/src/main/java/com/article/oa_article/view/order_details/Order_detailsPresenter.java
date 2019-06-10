package com.article.oa_article.view.order_details;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.api.http.TaskServiceImpl;
import com.article.oa_article.bean.CanEditTaskBO;
import com.article.oa_article.bean.OrderInfoBo;
import com.article.oa_article.bean.PenPaiTaskBO;
import com.article.oa_article.bean.TaskDetails;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.IdTypeRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Order_detailsPresenter extends BasePresenterImpl<Order_detailsContract.View>
        implements Order_detailsContract.Presenter {


    /**
     * 获取订单详情
     */
    public void getOrderInfo(IdTypeRequest request) {
        HttpServerImpl.getOrderInfo(request).subscribe(new HttpResultSubscriber<OrderInfoBo>() {
            @Override
            public void onSuccess(OrderInfoBo orderInfoBo) {
                if (mView != null) {
                    mView.getOrderInfo(orderInfoBo);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }


    /**
     * 获取任务列表
     */
    public void getTaskList(IdTypeRequest request) {
        request.setPageNum(1);
        request.setPageSize(1000);
        HttpServerImpl.getTaskList(request).subscribe(new HttpResultSubscriber<List<PenPaiTaskBO>>() {
            @Override
            public void onSuccess(List<PenPaiTaskBO> orderInfoBo) {
                if (mView != null) {
                    mView.getTaskList(orderInfoBo);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }

    /**
     * 获取任务列表是否可编辑
     */
    public void tashCanEdit(IdTypeRequest request) {
        TaskServiceImpl.taskCanEdit(request).subscribe(new HttpResultSubscriber<CanEditTaskBO>() {
            @Override
            public void onSuccess(CanEditTaskBO s) {
                if (mView != null) {
                    mView.taskCanEdit(s.getCanEdit() == 1);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }


    /**
     * 获取单个任务详情
     */
    public void getTaskInfo(int id) {
        IdRequest request = new IdRequest();
        request.setId(id);
        TaskServiceImpl.getTaskInfo(request).subscribe(new HttpResultSubscriber<TaskDetails>() {
            @Override
            public void onSuccess(TaskDetails s) {
                if (mView != null) {
                    mView.getTaskInfo(s);
                }
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }
}
