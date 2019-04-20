package com.wul.oa_article.bean.request;

public class TempleteRequest {


    /**
     * companyId : 0
     * name : string
     * pageNum : 0
     * pageSize : 0
     * token : string
     */

    private int companyId;
    private String name;
    private int pageNum;
    private int pageSize;
    private String token;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
