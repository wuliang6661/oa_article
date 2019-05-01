package com.article.oa_article.bean.request;

public class ForwordPassword {


    /**
     * confirmPassword : string
     * newPassword : string
     * token : string
     */

    private String confirmPassword;
    private String newPassword;
    private String token;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
