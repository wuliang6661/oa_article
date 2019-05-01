package com.article.oa_article.bean;

import java.io.Serializable;

public class MuBanTaskBO implements Serializable {


    /**
     * createDate : 1551405600000
     * id : 1
     * name : 制衣饰
     * remark : 制衣饰-4
     * templateId : 1
     * type : 0
     * updateDate : 1555818420000
     * userId : 4
     */

    private long createDate;
    private int id;
    private String name;
    private String remark;
    private int templateId;
    private int type;
    private long updateDate;
    private int userId;
    private String nickName;
    /**
     * taskType : 0
     * taskName : 制衣饰
     */

    private int taskType;
    private String taskName;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
