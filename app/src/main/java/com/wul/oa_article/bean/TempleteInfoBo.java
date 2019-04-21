package com.wul.oa_article.bean;

import com.wul.oa_article.bean.request.AddTempleteBo;

import java.util.List;

public class TempleteInfoBo {


    /**
     * templateInfo : {"companyId":1,"createDate":1551319200000,"id":1,"name":"修改制衣模板","remark":"沪小强制衣模板","updateDate":1552971527000,"userId":5}
     * templateDetailList : [{"createDate":1551405600000,"id":1,"name":"制衣饰","remark":"制衣饰-4","templateId":1,"type":0,"updateDate":1552971527000,"userId":4},{"createDate":1552971527000,"id":10,"name":"制帽饰","remark":"制帽饰-2","templateId":1,"type":1,"updateDate":1552971527000,"userId":2}]
     */

    private TemplateInfoBean templateInfo;
    private List<AddTempleteBo.TaskTemplateDetailsBean> templateDetailList;

    public TemplateInfoBean getTemplateInfo() {
        return templateInfo;
    }

    public void setTemplateInfo(TemplateInfoBean templateInfo) {
        this.templateInfo = templateInfo;
    }

    public List<AddTempleteBo.TaskTemplateDetailsBean> getTemplateDetailList() {
        return templateDetailList;
    }

    public void setTemplateDetailList(List<AddTempleteBo.TaskTemplateDetailsBean> templateDetailList) {
        this.templateDetailList = templateDetailList;
    }

    public static class TemplateInfoBean {
        /**
         * companyId : 1
         * createDate : 1551319200000
         * id : 1
         * name : 修改制衣模板
         * remark : 沪小强制衣模板
         * updateDate : 1552971527000
         * userId : 5
         */

        private int companyId;
        private long createDate;
        private int id;
        private String name;
        private String remark;
        private long updateDate;
        private int userId;

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public long getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(long updateDate) {
            this.updateDate = updateDate;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

}
