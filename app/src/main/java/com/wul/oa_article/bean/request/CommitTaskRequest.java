package com.wul.oa_article.bean.request;

public class CommitTaskRequest {


    /**
     * companyId : 0
     * taskId : 0
     * token : string
     */

    private int companyId;
    private int taskId;
    private String token;

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
