package com.article.oa_article.api;

import android.content.Context;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.article.oa_article.bean.PageBO;

import rx.Subscriber;

/**
 * Created by wuliang on 2018/11/13.
 * <p>
 * 自定义的Subscriber订阅者
 */

public abstract class HttpResultSubscriber<T> extends Subscriber<T> {

    /**
     * 滚动的菊花
     */
    private SVProgressHUD svProgressHUD;


    public HttpResultSubscriber() {

    }

    public HttpResultSubscriber(Context context) {
        svProgressHUD = new SVProgressHUD(context);
        svProgressHUD.showWithStatus("加载中...", SVProgressHUD.SVProgressHUDMaskType.BlackCancel);
    }


    @Override
    public void onNext(T t) {
        onSuccess(t);
        if (svProgressHUD != null && svProgressHUD.isShowing()) {
            svProgressHUD.dismiss();
        }
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (svProgressHUD != null && svProgressHUD.isShowing()) {
            svProgressHUD.dismiss();
        }

        onFiled(e.getMessage());
    }

    private PageBO pageBO;

    public void setPageInfo(PageBO pageInfo) {
        this.pageBO = pageInfo;
    }

    public PageBO getPageBO() {
        if (pageBO == null) {
            pageBO = new PageBO();
        }
        return pageBO;
    }


    @Override
    public void onCompleted() {

    }


    public abstract void onSuccess(T t);

    public abstract void onFiled(String message);
}
