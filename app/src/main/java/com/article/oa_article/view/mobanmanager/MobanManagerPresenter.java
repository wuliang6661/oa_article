package com.article.oa_article.view.mobanmanager;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.HttpServerImpl;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.MuBanTaskBO;
import com.article.oa_article.bean.TempleteBO;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.TempleteRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MobanManagerPresenter extends BasePresenterImpl<MobanManagerContract.View>
        implements MobanManagerContract.Presenter {


    public void getTempleteList(TempleteRequest request) {
        HttpServerImpl.getTempleteList(request).subscribe(new HttpResultSubscriber<List<TempleteBO>>() {
            @Override
            public void onSuccess(List<TempleteBO> s) {
                if (mView != null) {
                    mView.getMoBan(s);
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

    //使用模板
    public void makeMuBan(IdRequest request) {
        HttpServerImpl.makeMuBan(request).subscribe(new HttpResultSubscriber<List<MuBanTaskBO>>() {
            @Override
            public void onSuccess(List<MuBanTaskBO> s) {
                if (mView != null) {
                    mView.makeMuBanSoress(s);
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


    public void deleteTempter(int id) {
        PersonServiceImpl.deleteTemple(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.deleteSourss();
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


    public void moveTemplate(int firstId, int scondId) {
        HttpServerImpl.moveTemplate(firstId, scondId).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.moveSoruss();
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

