package com.article.oa_article.bean;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/1514:14
 * desc   :
 * version: 1.0
 */
public class UserOutInfo {


    /**
     * image : http://thirdwx.qlogo.cn/mmopen/vi_32/gAMcns1YFjiaroECTLQpxLM2xzjG5qkh9v00VP3PLH7KvDxT0ib2HT4pibAysq4wPYbJdo0cZib3u0wFubz5aickTUg/132
     * score : 0.0
     * phone : 18368163483
     * nickName : 梦中人
     * companys : [{"companyId":1,"unit":"","overdueRate":"0%","companyName":"小强浙江总公司","completeRate":"0%"}]
     */

    private String image;
    private double score;
    private String phone;
    private String nickName;
    private List<CompanysBean> companys;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<CompanysBean> getCompanys() {
        return companys;
    }

    public void setCompanys(List<CompanysBean> companys) {
        this.companys = companys;
    }

    public static class CompanysBean {
        /**
         * companyId : 1
         * unit :
         * overdueRate : 0%
         * companyName : 小强浙江总公司
         * completeRate : 0%
         */

        private int companyId;
        private String unit;
        private String overdueRate;
        private String companyName;
        private String completeRate;

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getOverdueRate() {
            return overdueRate;
        }

        public void setOverdueRate(String overdueRate) {
            this.overdueRate = overdueRate;
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
    }
}
