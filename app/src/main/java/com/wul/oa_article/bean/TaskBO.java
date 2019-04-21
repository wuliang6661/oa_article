package com.wul.oa_article.bean;

import java.util.List;

public class TaskBO {


    /**
     * task : {"unit":"","orderId":6,"nickName":"诸葛亮","num":"50000","taskName":"雨衣","remark":"","planCompleteDate":"2019-04-17","id":29,"parentId":0,"createName":"༄梦中人࿐","status":0}
     * parentTask : {}
     * taskHistory : []
     * order : {"orderSpecifications":[{"createDate":1554861454000,"id":35,"name":"腰带带子","num":10100,"orderId":29,"remark":"腰带带子10100条","size":"XL","unit":"条","updateDate":1554861454000},{"createDate":1554861454000,"id":36,"name":"腰带花饰","num":20200,"orderId":29,"remark":"腰带花饰20200条","size":"XL","unit":"个","updateDate":1554861454000}],"orderInfo":{"clientName":"沪小强","clientOrderName":"201903090010","clientOrderNum":"晚礼服整套","companyId":1,"companyOrderName":"201903090100","companyOrderNum":"晚礼服腰带","fileUrl":"www.wenjian.com/wenjian.doc","id":6,"image":[{"name":"图片","url":"www.image.com/image.jpg"}],"num":10000,"planCompleteDate":"2019-03-19","remark":"10000条，3,4两人做","status":1,"unit":"条","userId":5}}
     */

    private TaskBean task;
    private ParentTaskBean parentTask;
    private OrderInfoBo order;
    private List<?> taskHistory;

    public TaskBean getTask() {
        return task;
    }

    public void setTask(TaskBean task) {
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

    public static class TaskBean {
        /**
         * unit :
         * orderId : 6
         * nickName : 诸葛亮
         * num : 50000
         * taskName : 雨衣
         * remark :
         * planCompleteDate : 2019-04-17
         * id : 29
         * parentId : 0
         * createName : ༄梦中人࿐
         * status : 0
         */

        private String unit;
        private int orderId;
        private String nickName;
        private String num;
        private String taskName;
        private String remark;
        private String planCompleteDate;
        private int id;
        private int parentId;
        private String createName;
        private int status;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPlanCompleteDate() {
            return planCompleteDate;
        }

        public void setPlanCompleteDate(String planCompleteDate) {
            this.planCompleteDate = planCompleteDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class ParentTaskBean {



    }


}
