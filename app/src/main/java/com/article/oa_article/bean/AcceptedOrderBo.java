package com.article.oa_article.bean;

public class AcceptedOrderBo {


    /**
     * taskType : 1
     * companyOrderNum : 001201901300001
     * taskName : 造衣衣
     * id : 1
     * planCompleteDate : 2019-03-01
     * companyOrderName : 20190130鑫鑫采购订单
     * taskId : 1
     */

    private int taskType;
    private String companyOrderNum;
    private String taskName;
    private int id;
    private String planCompleteDate;
    private String companyOrderName;
    private int taskId;

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getCompanyOrderNum() {
        return companyOrderNum;
    }

    public void setCompanyOrderNum(String companyOrderNum) {
        this.companyOrderNum = companyOrderNum;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanCompleteDate() {
        return planCompleteDate;
    }

    public void setPlanCompleteDate(String planCompleteDate) {
        this.planCompleteDate = planCompleteDate;
    }

    public String getCompanyOrderName() {
        return companyOrderName;
    }

    public void setCompanyOrderName(String companyOrderName) {
        this.companyOrderName = companyOrderName;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
