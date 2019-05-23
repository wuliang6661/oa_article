package com.article.oa_article.bean.request;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2311:06
 * desc   :
 * version: 1.0
 */
public class LableRequest {


    /**
     * companyId : 0
     * token : string
     */

    private int companyId;
    private String token;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
