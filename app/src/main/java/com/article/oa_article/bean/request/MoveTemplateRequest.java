package com.article.oa_article.bean.request;

public class MoveTemplateRequest {


    /**
     * firstTemplateId : 0
     * secondTemplateId : 0
     * token : string
     */

    private int firstTemplateId;
    private int secondTemplateId;
    private String token;

    public int getFirstTemplateId() {
        return firstTemplateId;
    }

    public void setFirstTemplateId(int firstTemplateId) {
        this.firstTemplateId = firstTemplateId;
    }

    public int getSecondTemplateId() {
        return secondTemplateId;
    }

    public void setSecondTemplateId(int secondTemplateId) {
        this.secondTemplateId = secondTemplateId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
