package com.article.oa_article.module.chatline;

import com.article.oa_article.api.HttpResultSubscriber;
import com.article.oa_article.api.http.PersonServiceImpl;
import com.article.oa_article.bean.ChartBO;
import com.article.oa_article.bean.event.UpdateUnitEvent;
import com.article.oa_article.bean.request.AddOutRequest;
import com.article.oa_article.bean.request.ChartRequest;
import com.article.oa_article.mvp.BasePresenterImpl;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ChatLinePresenter extends BasePresenterImpl<ChatLineContract.View>
        implements ChatLineContract.Presenter {


    public void getbiaoData(ChartRequest request) {
        PersonServiceImpl.getChartData(request).subscribe(new HttpResultSubscriber<List<ChartBO>>() {
            @Override
            public void onSuccess(List<ChartBO> s) {
                if (mView != null) {
                    mView.getBiaoData(s);
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


    public void getChartData(ChartRequest request) {
        PersonServiceImpl.getChartData(request).subscribe(new HttpResultSubscriber<List<ChartBO>>() {
            @Override
            public void onSuccess(List<ChartBO> s) {
                if (mView != null) {
                    mView.getChatLine(s);
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


    public void addOutPut(AddOutRequest request) {
        PersonServiceImpl.addOutPut(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                if (mView != null) {
                    mView.addOutSoress();
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


    public void updateUnit(String unit) {
        PersonServiceImpl.updateUnit(unit).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
               if(mView != null){
                   EventBus.getDefault().post(new UpdateUnitEvent());
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
