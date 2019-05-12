package com.article.oa_article.bean.request;

public class DateTaskRequest {


    /**
     * companyId : 0
     * date : string
     * pageNum : 0
     * pageSize : 0
     * token : string
     * type : 0
     */

    private int companyId;
    private String date;
    private int pageNum;
    private int pageSize;
    private String token;
    private int type;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
