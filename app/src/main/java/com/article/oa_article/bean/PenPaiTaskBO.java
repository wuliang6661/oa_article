package com.article.oa_article.bean;

public class PenPaiTaskBO {


    /**
     * remainingDate : 344
     * planNum : 4343
     * unit : ds
     * nickName : 李四
     * canEdit : 0
     * taskName : 制衣饰品
     * id : 61
     * planCompleteDate : 1585670400000
     * actualNum : 200
     * userId : 4
     * parentId : 0
     * status : 1
     */

    private int remainingDate;
    private int planNum;
    private String unit;
    private String nickName;
    private int canEdit;
    private String taskName;
    private int id;
    private long planCompleteDate;
    private int  actualNum;
    private int userId;
    private int parentId;
    private int status;
    private int orderId;
    private String remark;
    private int addButton;

    public int getAddButton() {
        return addButton;
    }

    public void setAddButton(int addButton) {
        this.addButton = addButton;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getRemainingDate() {
        return remainingDate;
    }

    public void setRemainingDate(int remainingDate) {
        this.remainingDate = remainingDate;
    }

    public int getPlanNum() {
        return planNum;
    }

    public void setPlanNum(int planNum) {
        this.planNum = planNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(int canEdit) {
        this.canEdit = canEdit;
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

    public long getPlanCompleteDate() {
        return planCompleteDate;
    }

    public void setPlanCompleteDate(long planCompleteDate) {
        this.planCompleteDate = planCompleteDate;
    }

    public int getActualNum() {
        return actualNum;
    }

    public void setActualNum(int actualNum) {
        this.actualNum = actualNum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
