package com.wul.oa_article.bean;

public class TaskBO {


    /**
     * task : {"actualCompleteDate":1558540800000,"actualNum":600000,"companyId":1,"completeType":0,"createDate":1552060800000,"createId":5,"id":29,"orderId":6,"parentId":0,"planCompleteDate":1555430400000,"planNum":50000,"status":0,"taskLevel":0,"taskName":"雨衣","taskType":0,"userId":1}
     * order : {"orderSpecifications":[{"createDate":1554861454000,"id":35,"name":"腰带带子","num":10100,"orderId":29,"remark":"腰带带子10100条","size":"XL","unit":"条","updateDate":1554861454000},{"createDate":1554861454000,"id":36,"name":"腰带花饰","num":20200,"orderId":29,"remark":"腰带花饰20200条","size":"XL","unit":"个","updateDate":1554861454000}],"orderInfo":{"clientName":"沪小强","clientOrderName":"201903090010","clientOrderNum":"晚礼服整套","companyId":1,"companyOrderName":"201903090100","companyOrderNum":"晚礼服腰带","fileUrl":"www.wenjian.com/wenjian.doc","id":6,"image":[{}],"num":10000,"planCompleteDate":1552924800000,"remark":"10000条，3,4两人做","status":1,"unit":"条","userId":5}}
     */

    private TaskBean task;
    private OrderInfoBo order;

    public TaskBean getTask() {
        return task;
    }

    public void setTask(TaskBean task) {
        this.task = task;
    }

    public OrderInfoBo getOrder() {
        return order;
    }

    public void setOrder(OrderInfoBo order) {
        this.order = order;
    }

    public static class TaskBean {
        /**
         * actualCompleteDate : 1558540800000
         * actualNum : 600000
         * companyId : 1
         * completeType : 0
         * createDate : 1552060800000
         * createId : 5
         * id : 29
         * orderId : 6
         * parentId : 0
         * planCompleteDate : 1555430400000
         * planNum : 50000
         * status : 0
         * taskLevel : 0
         * taskName : 雨衣
         * taskType : 0
         * userId : 1
         */

        private long actualCompleteDate;
        private int actualNum;
        private int companyId;
        private int completeType;
        private long createDate;
        private int createId;
        private int id;
        private int orderId;
        private int parentId;
        private long planCompleteDate;
        private int planNum;
        private int status;
        private int taskLevel;
        private String taskName;
        private int taskType;
        private int userId;

        public long getActualCompleteDate() {
            return actualCompleteDate;
        }

        public void setActualCompleteDate(long actualCompleteDate) {
            this.actualCompleteDate = actualCompleteDate;
        }

        public int getActualNum() {
            return actualNum;
        }

        public void setActualNum(int actualNum) {
            this.actualNum = actualNum;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getCompleteType() {
            return completeType;
        }

        public void setCompleteType(int completeType) {
            this.completeType = completeType;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public int getCreateId() {
            return createId;
        }

        public void setCreateId(int createId) {
            this.createId = createId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public long getPlanCompleteDate() {
            return planCompleteDate;
        }

        public void setPlanCompleteDate(long planCompleteDate) {
            this.planCompleteDate = planCompleteDate;
        }

        public int getPlanNum() {
            return planNum;
        }

        public void setPlanNum(int planNum) {
            this.planNum = planNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTaskLevel() {
            return taskLevel;
        }

        public void setTaskLevel(int taskLevel) {
            this.taskLevel = taskLevel;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public int getTaskType() {
            return taskType;
        }

        public void setTaskType(int taskType) {
            this.taskType = taskType;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
