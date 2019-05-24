package com.article.oa_article.view.personmanager;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonManagerPresenter extends BasePresenterImpl<PersonManagerContract.View>
        implements PersonManagerContract.Presenter {


    public void getNeiUsers(String name) {
        IdRequest request = new IdRequest();
        request.setId(Integer.parseInt(MyApplication.getCommonId()));
        request.setName(name);
        PersonServiceImpl.getNeiPersonList(request).subscribe(new HttpResultSubscriber<List<BumenBO>>() {
            @Override
            public void onSuccess(List<BumenBO> s) {
                if (mView != null) {
                    mView.getPersonListByNeiBu(s);
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

    public void updateDeart(int userId, int deartId) {
        PersonServiceImpl.updateDeart(userId, deartId).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                getNeiUsers("");
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
