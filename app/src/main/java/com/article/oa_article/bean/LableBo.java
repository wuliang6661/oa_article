package com.article.oa_article.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2311:10
 * desc   :
 * version: 1.0
 */
public class LableBo {


    private List<SysLabelsBean> sysLabels;
    private List<CustomLabelsBean> customLabels;

    public List<SysLabelsBean> getSysLabels() {
        return sysLabels;
    }

    public void setSysLabels(List<SysLabelsBean> sysLabels) {
        this.sysLabels = sysLabels;
    }

    public List<CustomLabelsBean> getCustomLabels() {
        return customLabels;
    }

    public void setCustomLabels(List<CustomLabelsBean> customLabels) {
        this.customLabels = customLabels;
    }

    public static class SysLabelsBean implements Serializable {
        /**
         * name : 生产商
         * orderNum : 1
         * remark : 服饰部
         * Id : 2
         */

        private String name;
        private int orderNum;
        private String remark;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class CustomLabelsBean implements Serializable {
        /**
         * companyId : 1
         * createDate : 1552134572000
         * id : 5
         * name : 销售111
         * orderNum : 6
         * remark : string
         * type : 1
         * updateDate : 1553070052000
         * userId : 5
         */

        private int companyId;
        private long createDate;
        private int id;
        private String name;
        private int orderNum;
        private String remark;
        private int type;
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

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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
