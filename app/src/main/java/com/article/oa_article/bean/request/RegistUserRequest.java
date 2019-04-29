package com.article.oa_article.bean.request;

public class RegistUserRequest {


    /**
     * code : string
     * password : string
     * phone : string
     * type : 0
     */

    private String code;
    private String password;
    private String phone;
    private int type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
