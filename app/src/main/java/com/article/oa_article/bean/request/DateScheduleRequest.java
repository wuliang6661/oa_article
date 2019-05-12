package com.article.oa_article.bean.request;

public class DateScheduleRequest {


    /**
     * companyId : 0
     * date : string
     * token : string
     */

    private int companyId;
    private String date;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
