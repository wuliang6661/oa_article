package com.article.oa_article.view.bumenmanager;

import android.content.Context;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.BuMenFlowBO;
import com.article.oa_article.bean.request.BuMenRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BumenManagerPresenter extends BasePresenterImpl<BumenManagerContract.View>
        implements BumenManagerContract.Presenter {


    public void getBumenList(String name) {
        BuMenRequest request = new BuMenRequest();
        request.setName(name);
        PersonServiceImpl.getBumenList(request).subscribe(new HttpResultSubscriber<List<BuMenFlowBO>>() {
            @Override
            public void onSuccess(List<BuMenFlowBO> s) {
                if (mView != null) {
                    mView.getBumenList(s);
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


    public void addBuMen(String name) {
        PersonServiceImpl.addDeart(name).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                getBumenList("");
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
