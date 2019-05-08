package com.article.oa_article.bean.request;

public class PageRequest {


    /**
     * id : 0
     * pageNum : 0
     * pageSize : 0
     * token : string
     */

    private int id;
    private int pageNum;
    private int pageSize;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
