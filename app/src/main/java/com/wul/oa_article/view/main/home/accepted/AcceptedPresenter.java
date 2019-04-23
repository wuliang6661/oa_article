package com.wul.oa_article.view.main.home.accepted;

import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.base.MyApplication;
import com.wul.oa_article.bean.ClientOrderBo;
import com.wul.oa_article.bean.request.ClientInfoRequest;
import com.wul.oa_article.mvp.BasePresenterImpl;

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
                    mView.getClientInfo(s);
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
