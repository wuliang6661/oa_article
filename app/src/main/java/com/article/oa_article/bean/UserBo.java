package com.article.oa_article.bean;

import java.util.List;

public class UserBo {


    /**
     * name : 李四
     * id : 4
     * phone : 12312345678
     * companys : [{"id":3,"companyName":"小强京丝上海分公司"},{"id":4,"companyName":"小强杭州分公司"}]
     */

    private String name;
    private int id;
    private String phone;
    private List<CompanysBean> companys;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<CompanysBean> getCompanys() {
        return companys;
    }

    public void setCompanys(List<CompanysBean> companys) {
        this.companys = companys;
    }

    public static class CompanysBean {
        /**
         * id : 3
         * companyName : 小强京丝上海分公司
         */

        private int id;
        private String companyName;
        private int isAdmin;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(int isAdmin) {
            this.isAdmin = isAdmin;
        }
    }
}
