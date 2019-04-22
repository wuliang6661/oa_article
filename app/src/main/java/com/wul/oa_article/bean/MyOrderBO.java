package com.wul.oa_article.bean;

public class MyOrderBO {


    /**
     * remainingDate : -26
     * companyOrderNum : 001201901300001
     * orderId : 1
     * nickName : 张三
     * taskName : 造衣衣
     * id : 1
     * planCompleteDate : 2019-03-01
     * companyOrderName : 20190130鑫鑫采购订单
     * userId : 3
     * actualCompleteDate : 2019-03-07
     * status : 0
     */

    private String remainingDate;
    private String companyOrderNum;
    private int orderId;
    private String nickName;
    private String taskName;
    private int id;
    private String planCompleteDate;
    private String companyOrderName;
    private int userId;
    private String actualCompleteDate;
    private int status;
    private int isMe;

    public int getIsMe() {
        return isMe;
    }

    public void setIsMe(int isMe) {
        this.isMe = isMe;
    }

    public String getRemainingDate() {
        return remainingDate;
    }

    public void setRemainingDate(String remainingDate) {
        this.remainingDate = remainingDate;
    }

    public String getCompanyOrderNum() {
        return companyOrderNum;
    }

    public void setCompanyOrderNum(String companyOrderNum) {
        this.companyOrderNum = companyOrderNum;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getActualCompleteDate() {
        return actualCompleteDate;
    }

    public void setActualCompleteDate(String actualCompleteDate) {
        this.actualCompleteDate = actualCompleteDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
