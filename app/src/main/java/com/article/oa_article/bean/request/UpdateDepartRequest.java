package com.article.oa_article.bean.request;

public class UpdateDepartRequest {


    /**
     * companyId : 0
     * departId : 0
     * token : string
     * userId : 0
     */

    private int companyId;
    private int departId;
    private String token;
    private int userId;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getDepartId() {
        return departId;
    }

    public void setDepartId(int departId) {
        this.departId = departId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
