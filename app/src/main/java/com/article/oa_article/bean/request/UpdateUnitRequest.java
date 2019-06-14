package com.article.oa_article.bean.request;

public class UpdateUnitRequest {


    /**
     * id : 0
     * token : string
     * unit : string
     */

    private int id;
    private String token;
    private String unit;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
