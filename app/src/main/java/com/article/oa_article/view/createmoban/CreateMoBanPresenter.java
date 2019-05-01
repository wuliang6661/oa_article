package com.article.oa_article.view.createmoban;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.bean.TempleteInfoBo;
import com.article.oa_article.bean.request.AddTempleteBo;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

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
        IdRequest request = new IdRequest();
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
