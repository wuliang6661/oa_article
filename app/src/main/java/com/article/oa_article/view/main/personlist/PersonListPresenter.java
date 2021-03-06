package com.article.oa_article.view.main.personlist;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.BumenBO;
import com.article.oa_article.bean.ShareBo;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PersonListPresenter extends BasePresenterImpl<PersonListContract.View>
        implements PersonListContract.Presenter {


    public void getNeiUsers(IdRequest request) {
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


    public void getOutUsers(IdRequest request) {
        PersonServiceImpl.getOutPersonList(request).subscribe(new HttpResultSubscriber<List<BumenBO>>() {
            @Override
            public void onSuccess(List<BumenBO> s) {
                if (mView != null) {
                    mView.getPersonListByWaiBu(s);
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


    public void getShareMsg(int flag) {
        PersonServiceImpl.getShareMsg().subscribe(new HttpResultSubscriber<ShareBo>() {
            @Override
            public void onSuccess(ShareBo s) {
                if (mView != null) {
                    mView.getShare(flag, s);
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
