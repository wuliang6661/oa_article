package com.article.oa_article.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/1512:32
 * desc   :
 * version: 1.0
 */
public class UserInInfoBo {


    /**
     * image : http://thirdwx.qlogo.cn/mmopen/vi_32/6p7YWGXJhrHUbHvLxibwklgLM1ZGWT1uM2lDyjHTj44Dd6uDNjwNU8oibX7k5OiabdwcZcHIic8HibXW090ico6JOibxw/132
     * phone : 12312345678
     * overdueRate : 0%
     * nickName : 李四
     * companyName : 小强浙江总公司
     * completeRate : 0%
     * departName : 行政部
     */

    private String image;
    private String phone;
    private String overdueRate;
    private String nickName;
    private String companyName;
    private String completeRate;
    private String departName;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOverdueRate() {
        return overdueRate;
    }

    public void setOverdueRate(String overdueRate) {
        this.overdueRate = overdueRate;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompleteRate() {
        return completeRate;
    }

    public void setCompleteRate(String completeRate) {
        this.completeRate = completeRate;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }
}
