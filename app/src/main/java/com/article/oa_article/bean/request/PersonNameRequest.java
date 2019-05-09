package com.article.oa_article.bean.request;

public class PersonNameRequest {


    /**
     * newNickName : string
     * token : string
     */

    private String newNickName;
    private String token;

    public String getNewNickName() {
        return newNickName;
    }

    public void setNewNickName(String newNickName) {
        this.newNickName = newNickName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
