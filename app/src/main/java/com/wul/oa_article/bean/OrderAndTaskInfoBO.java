package com.wul.oa_article.bean;

import com.wul.oa_article.bean.request.AddTaskRequest;

import java.util.List;

public class OrderAndTaskInfoBO {


    /**
     * taskList : [{"planNum":2,"unit":"杭州","nickName":"༄梦中人࿐","taskName":"这","id":49,"planCompleteDate":1555948800000,"actualNum":"0","status":4},{"planNum":2,"unit":"杭州","nickName":"༄梦中人࿐","taskName":"这","id":50,"planCompleteDate":1555948800000,"actualNum":"0","status":4},{"remainingDate":1,"planNum":2,"unit":"杭州","nickName":"༄梦中人࿐","taskName":"这","id":51,"planCompleteDate":1555948800000,"actualNum":"0","status":0}]
     * order : {"orderSpecifications":[],"orderInfo":{"clientName":"","clientNum":0,"clientOrderName":"","clientOrderNum":"","clientUnit":"","companyId":1,"companyOrderName":"这","companyOrderNum":"杭州","id":171,"image":[{"name":"IMG_1683","url":"http://xiaoqiangzhizao.oss-cn-hangzhou.aliyuncs.com/xqzz/2019041916/201904191616101132270.png"}],"num":2,"remark":"","status":1,"unit":"杭州","userId":5}}
     */

    private OrderInfoBo order;
    private List<AddTaskRequest.OrderTasksBean> taskList;

    public OrderInfoBo getOrder() {
        return order;
    }

    public void setOrder(OrderInfoBo order) {
        this.order = order;
    }

    public List<AddTaskRequest.OrderTasksBean> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<AddTaskRequest.OrderTasksBean> taskList) {
        this.taskList = taskList;
    }


}
