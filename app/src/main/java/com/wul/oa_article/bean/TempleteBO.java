package com.wul.oa_article.bean;

public class TempleteBO {


    /**
     * taskType : 0
     * nickName : 李四
     * name : 修改制衣模板
     * taskName : 制衣饰
     * id : 1
     * userId : 4
     * remarks : 沪小强制衣模板
     */

    private int taskType;
    private String nickName;
    private String name;
    private String taskName;
    private int id;
    private int userId;
    private String remarks;

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
