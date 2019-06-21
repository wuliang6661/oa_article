package com.article.oa_article.bean.request;

public class ApplyComplanRequest {


    /**
     * hisCompanyId : 0
     * myCompanyId : 0
     * token : string
     */

    private int hisCompanyId;
    private int myCompanyId;
    private String token;

    public int getHisCompanyId() {
        return hisCompanyId;
    }

    public void setHisCompanyId(int hisCompanyId) {
        this.hisCompanyId = hisCompanyId;
    }

    public int getMyCompanyId() {
        return myCompanyId;
    }

    public void setMyCompanyId(int myCompanyId) {
        this.myCompanyId = myCompanyId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
