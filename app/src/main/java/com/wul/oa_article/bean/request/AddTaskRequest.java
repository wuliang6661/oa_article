package com.wul.oa_article.bean.request;

import java.util.List;

public class AddTaskRequest {


    /**
     * companyId : 0
     * objectId : 0
     * orderTasks : [{"actualCompleteDate":"2019-04-13T07:07:06.470Z","actualNum":0,"companyId":0,"completeType":0,"createDate":"2019-04-13T07:07:06.470Z","createId":0,"id":0,"orderId":0,"parentId":0,"planCompleteDate":"2019-04-13T07:07:06.470Z","planNum":0,"remark":"string","status":0,"taskLevel":0,"taskName":"string","taskType":0,"unit":"string","updateDate":"2019-04-13T07:07:06.470Z","userId":0}]
     * token : string
     */

    private int companyId;
    private int objectId;
    private String token;
    private List<OrderTasksBean> orderTasks;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<OrderTasksBean> getOrderTasks() {
        return orderTasks;
    }

    public void setOrderTasks(List<OrderTasksBean> orderTasks) {
        this.orderTasks = orderTasks;
    }

    public static class OrderTasksBean {
        /**
         * actualCompleteDate : 2019-04-13T07:07:06.470Z
         * actualNum : 0
         * companyId : 0
         * completeType : 0
         * createDate : 2019-04-13T07:07:06.470Z
         * createId : 0
         * id : 0
         * orderId : 0
         * parentId : 0
         * planCompleteDate : 2019-04-13T07:07:06.470Z
         * planNum : 0
         * remark : string
         * status : 0
         * taskLevel : 0
         * taskName : string
         * taskType : 0
         * unit : string
         * updateDate : 2019-04-13T07:07:06.470Z
         * userId : 0
         */

        private String actualCompleteDate;
        private int actualNum;
        private int companyId;
        private int completeType;
        private String createDate;
        private int createId;
        private int id;
        private int orderId;
        private int parentId;
        private String planCompleteDate;
        private int planNum;
        private String remark;
        private int status;
        private int taskLevel;
        private String taskName;
        private int taskType;
        private String unit;
        private String updateDate;
        private int userId;

        public String getActualCompleteDate() {
            return actualCompleteDate;
        }

        public void setActualCompleteDate(String actualCompleteDate) {
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

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
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

        public String getPlanCompleteDate() {
            return planCompleteDate;
        }

        public void setPlanCompleteDate(String planCompleteDate) {
            this.planCompleteDate = planCompleteDate;
        }

        public int getPlanNum() {
            return planNum;
        }

        public void setPlanNum(int planNum) {
            this.planNum = planNum;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
