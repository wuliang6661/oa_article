package com.wul.oa_article.bean.request;

import com.wul.oa_article.view.createorder.ImageBO;
import com.wul.oa_article.view.createorder.PingLeiBO;

import java.util.List;

public class CreateOrderBO {


    /**
     * clientCompleteDate : 2019-04-11T04:21:04.458Z
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
     * images : [{"name":"string","url":"string"}]
     * orderNum : 0
     * orderSpecifications : [{"createDate":"2019-04-11T04:21:04.458Z","id":0,"name":"string","num":0,"orderId":0,"remark":"string","size":"string","unit":"string","updateDate":"2019-04-11T04:21:04.459Z"}]
     * orderUnit : string
     * parentOrderTaskId : 0
     * planCompleteDate : 2019-04-11T04:21:04.459Z
     * remark : string
     * token : string
     */

    private String clientCompleteDate;
    private String clientName;
    private String clientNum;
    private String clientOrderName;
    private String clientOrderNum;
    private String clientUnit;
    private String companyId;
    private String companyOrderName;
    private String companyOrderNum;
    private String fileUrl;
    private int id;
    private String orderNum;
    private String orderUnit;
    private int parentOrderTaskId;
    private String planCompleteDate;
    private String remark;
    private String token;
    private List<ImageBO> images;
    private List<PingLeiBO> orderSpecifications;

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

    public String getClientNum() {
        return clientNum;
    }

    public void setClientNum(String clientNum) {
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
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

    public List<ImageBO> getImages() {
        return images;
    }

    public void setImages(List<ImageBO> images) {
        this.images = images;
    }

    public List<PingLeiBO> getOrderSpecifications() {
        return orderSpecifications;
    }

    public void setOrderSpecifications(List<PingLeiBO> orderSpecifications) {
        this.orderSpecifications = orderSpecifications;
    }


}
