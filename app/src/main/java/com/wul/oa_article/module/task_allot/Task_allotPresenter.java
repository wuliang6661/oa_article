package com.wul.oa_article.module.task_allot;

import android.content.Context;

import com.wul.oa_article.api.HttpResultSubscriber;
import com.wul.oa_article.api.http.TaskServiceImpl;
import com.wul.oa_article.bean.request.AddTaskRequest;
import com.wul.oa_article.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class Task_allotPresenter extends BasePresenterImpl<Task_allotContract.View>
        implements Task_allotContract.Presenter{


    public void addTaskByOrder(AddTaskRequest request){
        TaskServiceImpl.addTaskByOrder(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String message) {

            }
        });
    }
    
}
