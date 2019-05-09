package com.article.oa_article.bean.request;

public class PersonPasswordRequest {


    /**
     * confirmPassword : string
     * newPassword : string
     * oldPassword : string
     * token : string
     */

    private String confirmPassword;
    private String newPassword;
    private String oldPassword;
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
