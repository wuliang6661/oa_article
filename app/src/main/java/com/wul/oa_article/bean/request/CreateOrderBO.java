package com.wul.oa_article.bean.request;

import java.util.List;

public class CreateOrderBO {


    /**
     * clientCompleteDate : 2019-04-10T13:11:09.468Z
     * clientName : string
     * clientNum : 0
     * clientOrderName : string
     * clientOrderNum : string
     * clientUnit : string
     * companyId : 0
     * companyOrderName : string
     * companyOrderNum : string
     * fileUrl : string
     * id : 0
     * imageUrl : string
     * orderNum : 0
     * orderSpecifications : [{"createDate":"2019-04-10T13:11:09.468Z","id":0,"name":"string","num":0,"orderId":0,"remark":"string","size":"string","unit":"string","updateDate":"2019-04-10T13:11:09.468Z"}]
     * orderUnit : string
     * parentOrderTaskId : 0
     * planCompleteDate : 2019-04-10T13:11:09.468Z
     * remark : string
     * token : string
     */

    private String clientCompleteDate;
    private String clientName;
    private int clientNum;
    private String clientOrderName;
    private String clientOrderNum;
    private String clientUnit;
    private int companyId;
    private String companyOrderName;
    private String companyOrderNum;
    private String fileUrl;
    private int id;
    private String imageUrl;
    private int orderNum;
    private String orderUnit;
    private int parentOrderTaskId;
    private String planCompleteDate;
    private String remark;
    private String token;
    private List<OrderSpecificationsBean> orderSpecifications;

    public String getClientCompleteDate() {
        return clientCompleteDate;
    }

    public void setClientCompleteDate(String clientCompleteDate) {
        this.clientCompleteDate = clientCompleteDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getClientNum() {
        return clientNum;
    }

    public void setClientNum(int clientNum) {
        this.clientNum = clientNum;
    }

    public String getClientOrderName() {
        return clientOrderName;
    }

    public void setClientOrderName(String clientOrderName) {
        this.clientOrderName = clientOrderName;
    }

    public String getClientOrderNum() {
        return clientOrderNum;
    }

    public void setClientOrderNum(String clientOrderNum) {
        this.clientOrderNum = clientOrderNum;
    }

    public String getClientUnit() {
        return clientUnit;
    }

    public void setClientUnit(String clientUnit) {
        this.clientUnit = clientUnit;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyOrderName() {
        return companyOrderName;
    }

    public void setCompanyOrderName(String companyOrderName) {
        this.companyOrderName = companyOrderName;
    }

    public String getCompanyOrderNum() {
        return companyOrderNum;
    }

    public void setCompanyOrderNum(String companyOrderNum) {
        this.companyOrderNum = companyOrderNum;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }

    public int getParentOrderTaskId() {
        return parentOrderTaskId;
    }

    public void setParentOrderTaskId(int parentOrderTaskId) {
        this.parentOrderTaskId = parentOrderTaskId;
    }

    public String getPlanCompleteDate() {
        return planCompleteDate;
    }

    public void setPlanCompleteDate(String planCompleteDate) {
        this.planCompleteDate = planCompleteDate;
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

    public List<OrderSpecificationsBean> getOrderSpecifications() {
        return orderSpecifications;
    }

    public void setOrderSpecifications(List<OrderSpecificationsBean> orderSpecifications) {
        this.orderSpecifications = orderSpecifications;
    }

    public static class OrderSpecificationsBean {
        /**
         * createDate : 2019-04-10T13:11:09.468Z
         * id : 0
         * name : string
         * num : 0
         * orderId : 0
         * remark : string
         * size : string
         * unit : string
         * updateDate : 2019-04-10T13:11:09.468Z
         */

        private String createDate;
        private int id;
        private String name;
        private int num;
        private int orderId;
        private String remark;
        private String size;
        private String unit;
        private String updateDate;

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

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }
    }
}
