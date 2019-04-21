package com.wul.oa_article.view.createmoban;

import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.bean.TempleteInfoBo;
import com.wul.oa_article.bean.request.AddTempleteBo;
import com.wul.oa_article.bean.request.OrderQueryRequest;
import com.wul.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CreateMoBanPresenter extends BasePresenterImpl<CreateMoBanContract.View>
        implements CreateMoBanContract.Presenter {

    /**
     * 获取模板详情
     */
    public void getMoBanInfo(int id) {
        OrderQueryRequest request = new OrderQueryRequest();
        request.setId(id);
        HttpServerImpl.getTempleteInfo(request).subscribe(new HttpResultSubscriber<TempleteInfoBo>() {
            @Override
            public void onSuccess(TempleteInfoBo s) {
                if (mView != null) {
                    mView.getMoBanInfo(s);
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
     * 新增模板
     */
    public void addTemplete(AddTempleteBo addTempleteBo) {
        HttpServerImpl.addTemplete(addTempleteBo).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.onSuress();
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
     * 修改模板
     */
    public void editTemplete(AddTempleteBo addTempleteBo) {
        HttpServerImpl.editTemplete(addTempleteBo).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.onSuress();
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
