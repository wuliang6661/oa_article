package com.article.oa_article.bean;

import com.article.oa_article.bean.request.AddTaskRequest;

import java.util.List;

public class TaskBO {


    /**
     * task : {"unit":"","orderId":6,"nickName":"诸葛亮","num":"50000","taskName":"雨衣","remark":"","planCompleteDate":"2019-04-17","id":29,"parentId":0,"createName":"༄梦中人࿐","status":0}
     * parentTask : {}
     * taskHistory : []
     * order : {"orderSpecifications":[{"createDate":1554861454000,"id":35,"name":"腰带带子","num":10100,"orderId":29,"remark":"腰带带子10100条","size":"XL","unit":"条","updateDate":1554861454000},{"createDate":1554861454000,"id":36,"name":"腰带花饰","num":20200,"orderId":29,"remark":"腰带花饰20200条","size":"XL","unit":"个","updateDate":1554861454000}],"orderInfo":{"clientName":"沪小强","clientOrderName":"201903090010","clientOrderNum":"晚礼服整套","companyId":1,"companyOrderName":"201903090100","companyOrderNum":"晚礼服腰带","fileUrl":"www.wenjian.com/wenjian.doc","id":6,"image":[{"name":"图片","url":"www.image.com/image.jpg"}],"num":10000,"planCompleteDate":"2019-03-19","remark":"10000条，3,4两人做","status":1,"unit":"条","userId":5}}
     */

    private AddTaskRequest.OrderTasksBean task;
    private ParentTaskBean parentTask;
    private OrderInfoBo order;
    private List<?> taskHistory;

    public AddTaskRequest.OrderTasksBean getTask() {
        return task;
    }

    public void setTask(AddTaskRequest.OrderTasksBean task) {
        this.task = task;
    }

    public ParentTaskBean getParentTask() {
        return parentTask;
    }

    public void setParentTask(ParentTaskBean parentTask) {
        this.parentTask = parentTask;
    }

    public OrderInfoBo getOrder() {
        return order;
    }

    public void setOrder(OrderInfoBo order) {
        this.order = order;
    }

    public List<?> getTaskHistory() {
        return taskHistory;
    }

    public void setTaskHistory(List<?> taskHistory) {
        this.taskHistory = taskHistory;
    }


    public static class ParentTaskBean {


    }


}
