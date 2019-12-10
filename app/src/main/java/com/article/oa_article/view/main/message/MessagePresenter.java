package com.article.oa_article.view.main.message;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.MessageServiceImpl;
import com.article.oa_article.base.MyApplication;
import com.article.oa_article.bean.MsgBO;
import com.article.oa_article.bean.request.IdRequest;
import com.article.oa_article.bean.request.PageRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MessagePresenter extends BasePresenterImpl<MessageContract.View>
        implements MessageContract.Presenter {


    public void getMessageList(int id) {
        PageRequest request = new PageRequest();
        request.setId(id);
        MessageServiceImpl.getMessageList(request).subscribe(new HttpResultSubscriber<List<MsgBO>>() {
            @Override
            public void onSuccess(List<MsgBO> s) {
                if (mView != null) {
                    mView.getMsgList(s);
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


    public void getReadCount(int id) {
        IdRequest request = new IdRequest();
        request.setId(id);
        MessageServiceImpl.getNoReadCounts(request).subscribe(new HttpResultSubscriber<Integer>() {
            @Override
            public void onSuccess(Integer s) {
                if (mView != null) {
                    mView.getNoReadCount(s);
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


    public void setMsgRead(String ids, int readStatus) {
        MessageServiceImpl.readMsgType(ids, readStatus).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.readSuress();
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


    public void readAllMsg() {
        MessageServiceImpl.readAllMsg().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    getMessageList(Integer.parseInt(MyApplication.getCommonId()));
                    getReadCount(Integer.parseInt(MyApplication.getCommonId()));
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


    public void delAllMsg() {
        MessageServiceImpl.delAllMsg().subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    getMessageList(Integer.parseInt(MyApplication.getCommonId()));
                    getReadCount(Integer.parseInt(MyApplication.getCommonId()));
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
