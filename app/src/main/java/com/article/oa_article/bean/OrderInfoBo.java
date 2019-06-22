package com.article.oa_article.bean;

import com.article.oa_article.module.create_order.ImageBO;
import com.article.oa_article.module.create_order.PingLeiBO;

import java.io.Serializable;
import java.util.List;

public class OrderInfoBo implements Serializable {


    /**
     * orderSpecifications : []
     * orderInfo : {"clientName":"zhan","clientNum":0,"clientOrderName":"zhangsan","clientOrderNum":"87788888888888888888","companyId":0,"companyOrderName":"Zhang's an","companyOrderNum":"87787666666666666666","id":149,"image":[],"num":3322,"planCompleteDate":1556553600000,"remark":"","status":1,"unit":"zhan","userId":6}
     */

    private OrderInfoBean orderInfo;
    private List<PingLeiBO> orderSpecifications;


    public OrderInfoBean getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<PingLeiBO> getOrderSpecifications() {
        return orderSpecifications;
    }

    public void setOrderSpecifications(List<PingLeiBO> orderSpecifications) {
        this.orderSpecifications = orderSpecifications;
    }

    public static class OrderInfoBean implements Serializable {
        /**
         * clientName : zhan
         * clientNum : 0
         * clientOrderName : zhangsan
         * clientOrderNum : 87788888888888888888
         * companyId : 0
         * companyOrderName : Zhang's an
         * companyOrderNum : 87787666666666666666
         * id : 149
         * image : []
         * num : 3322
         * planCompleteDate : 1556553600000
         * remark :
         * status : 1
         * unit : zhan
         * userId : 6
         */

        private String clientName;
        private int clientNum;
        private String clientOrderName;
        private String clientOrderNum;
        private int companyId;
        private String companyOrderName;
        private String companyOrderNum;
        private int id;
        private int num;
        private long planCompleteDate;
        private String remark;
        private int parentOrderTaskId;

        public int getParentOrderTaskId() {
            return parentOrderTaskId;
        }

        public void setParentOrderTaskId(int parentOrderTaskId) {
            this.parentOrderTaskId = parentOrderTaskId;
        }

        /**
         * 状态（0待接受，1进行中，2已完成，3已取消）
         */
        private int status;
        private String unit;
        private int userId;
        private List<ImageBO> image;
        private String nickName;
        private int canEdit;


        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public long getPlanCompleteDate() {
            return planCompleteDate;
        }

        public void setPlanCompleteDate(long planCompleteDate) {
            this.planCompleteDate = planCompleteDate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<ImageBO> getImage() {
            return image;
        }

        public void setImage(List<ImageBO> image) {
            this.image = image;
        }

        public int getCanEdit() {
            return canEdit;
        }

        public void setCanEdit(int canEdit) {
            this.canEdit = canEdit;
        }

    }
}
