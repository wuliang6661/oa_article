package com.article.oa_article.bean.request;

public class UpdateCompanyUnitRequest {


    /**
     * companyId : 0
     * companyUnitId : 0
     * token : string
     */

    private int companyId;
    private int companyUnitId;
    private String token;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getCompanyUnitId() {
        return companyUnitId;
    }

    public void setCompanyUnitId(int companyUnitId) {
        this.companyUnitId = companyUnitId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
