package com.wul.oa_article.bean.request;

public class OrderQueryRequest {


    /**
     * id : 0
     * token : string
     */

    private int id;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
