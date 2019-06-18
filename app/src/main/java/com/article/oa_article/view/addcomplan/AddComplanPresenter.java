package com.article.oa_article.view.addcomplan;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.ApplyComplanBO;
import com.article.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddComplanPresenter extends BasePresenterImpl<AddComplanContract.View>
        implements AddComplanContract.Presenter {

    /**
     * 查询公司列表
     */
    public void getComplanList(String name) {
        PersonServiceImpl.getComplanList(name).subscribe(new HttpResultSubscriber<List<ApplyComplanBO>>() {
            @Override
            public void onSuccess(List<ApplyComplanBO> s) {
                if (mView != null) {
                    mView.getComplanList(s);
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
     * 申请加入公司
     */
    public void applyComplan(int id) {
        PersonServiceImpl.addComplan(id).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.addSounrss();
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
