package com.article.oa_article.bean.request;

import java.util.List;

public class AddTempleteBo {


    /**
     * companyId : 0
     * name : string
     * remark : string
     * taskTemplateDetails : [{"createDate":"2019-04-20T02:39:18.837Z","id":0,"name":"string","remark":"string","templateId":0,"type":0,"updateDate":"2019-04-20T02:39:18.837Z","userId":0}]
     * token : string
     */

    private int companyId;
    private String name;
    private String remark;
    private String token;
    private List<TaskTemplateDetailsBean> taskTemplateDetails;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<TaskTemplateDetailsBean> getTaskTemplateDetails() {
        return taskTemplateDetails;
    }

    public void setTaskTemplateDetails(List<TaskTemplateDetailsBean> taskTemplateDetails) {
        this.taskTemplateDetails = taskTemplateDetails;
    }

    public static class TaskTemplateDetailsBean {
        /**
         * createDate : 2019-04-20T02:39:18.837Z
         * id : 0
         * name : string
         * remark : string
         * templateId : 0
         * type : 0
         * updateDate : 2019-04-20T02:39:18.837Z
         * userId : 0
         */

        private String createDate;
        private int id;
        private String name;
        private String remark;
        private int templateId;
        private int type;
        private String updateDate;
        private int userId;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
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

        public int getTemplateId() {
            return templateId;
        }

        public void setTemplateId(int templateId) {
            this.templateId = templateId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
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
