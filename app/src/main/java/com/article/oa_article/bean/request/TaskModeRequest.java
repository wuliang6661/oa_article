package com.article.oa_article.bean.request;

public class TaskModeRequest {


    /**
     * completeType : 0
     * taskId : 0
     * token : string
     */

    private int completeType;
    private int taskId;
    private String token;

    public int getCompleteType() {
        return completeType;
    }

    public void setCompleteType(int completeType) {
        this.completeType = completeType;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
