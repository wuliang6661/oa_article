package com.article.oa_article.view.main.home.accepted;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.bean.ClientOrderBo;
import com.article.oa_article.bean.request.ClientInfoRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AcceptedPresenter extends BasePresenterImpl<AcceptedContract.View>
        implements AcceptedContract.Presenter {


    //获取外部订单数据
    public void getWaiBuOrder(int taskId, int complanId) {
        ClientInfoRequest request = new ClientInfoRequest();
        request.setCompanyId(complanId);
        request.setTaskId(taskId);
        HttpServerImpl.getClientInfo(request).subscribe(new HttpResultSubscriber<ClientOrderBo>() {
            @Override
            public void onSuccess(ClientOrderBo s) {
                if (mView != null) {
                    mView.getClientInfo(taskId, s);
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
     * 选择公司
     */
    public void selectComplan(int taskId, int complanId) {
        HttpServerImpl.choiceComplan(taskId, complanId).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.selectComplanSouress(taskId,complanId);
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
