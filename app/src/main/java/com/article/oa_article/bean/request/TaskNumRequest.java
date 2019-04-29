package com.article.oa_article.bean.request;

public class TaskNumRequest {


    /**
     * actualNum : 0
     * companyId : 0
     * taskId : 0
     * token : string
     */

    private int actualNum;
    private int companyId;
    private int taskId;
    private String token;

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
