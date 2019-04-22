package com.wul.oa_article.bean.request;

public class ShunYanRequest {

    /**
     * day : 0
     * id : 0
     * token : string
     * type : 0
     */

    private int day;
    private int id;
    private String token;
    private int type;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
