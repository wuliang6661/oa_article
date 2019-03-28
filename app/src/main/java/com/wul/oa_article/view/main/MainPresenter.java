package com.wul.oa_article.view.main;

import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.HttpServerImpl;
import com.wul.oa_article.bean.SalesBo;
import com.wul.oa_article.bean.request.SelectRequest;
import com.wul.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {

    /**
     * 获取常用搜索记录
     */
    public void getCommplayList(SelectRequest request) {
        HttpServerImpl.getCommonLyHistory(request).subscribe(new HttpResultSubscriber<List<SalesBo>>() {
            @Override
            public void onSuccess(List<SalesBo> s) {
                if (mView != null) {
                    mView.getSales(s);
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
