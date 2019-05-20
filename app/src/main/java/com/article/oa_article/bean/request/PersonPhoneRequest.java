package com.article.oa_article.bean.request;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2017:33
 * desc   :
 * version: 1.0
 */
public class PersonPhoneRequest {


    /**
     * code : string
     * newPhone : string
     * token : string
     */

    private String code;
    private String newPhone;
    private String token;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
