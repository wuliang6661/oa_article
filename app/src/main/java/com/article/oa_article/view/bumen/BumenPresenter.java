package com.article.oa_article.view.bumen;

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

public class BumenPresenter extends BasePresenterImpl<BumenContract.View> implements BumenContract.Presenter {

    public void getBumenList() {
        BuMenRequest request = new BuMenRequest();
        request.setName("");
        PersonServiceImpl.getBumenList(request).subscribe(new HttpResultSubscriber<List<BuMenFlowBO>>() {
            @Override
            public void onSuccess(List<BuMenFlowBO> s) {
                if (mView != null) {
                    mView.getBumenFlows(s);
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
                getBumenList();
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }


    public void deleteDeart(String id) {
        PersonServiceImpl.deleteDeart(Integer.parseInt(id)).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                getBumenList();
            }

            @Override
            public void onFiled(String message) {
                if (mView != null) {
                    mView.onRequestError(message);
                }
            }
        });
    }


    public void updateDeart(int userId, int deartId) {
        PersonServiceImpl.updateDeart(userId, deartId).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.updateDeats();
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
