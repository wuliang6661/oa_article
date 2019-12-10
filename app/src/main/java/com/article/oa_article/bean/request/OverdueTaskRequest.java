package com.article.oa_article.bean.request;

public class OverdueTaskRequest {


    /**
     * companyId : 0
     * pageNum : 0
     * pageSize : 0
     * token : string
     * userId : 0
     */

    private int companyId;
    private int pageNum;
    private int pageSize;
    private String token;
    private int userId;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
