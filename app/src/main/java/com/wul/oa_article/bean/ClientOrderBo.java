package com.wul.oa_article.bean;

import java.io.Serializable;

public class ClientOrderBo implements Serializable {


    /**
     * clientOrderNum : 在
     * clientName : 杭小强
     * clientUnit : 在
     * clientNum : 2
     * clientCompleteDate : 2019/04/17
     * clientOrderName : 这边风景独好
     */

    private String clientOrderNum;
    private String clientName;
    private String clientUnit;
    private int clientNum;
    private String clientCompleteDate;
    private String clientOrderName;

    public String getClientOrderNum() {
        return clientOrderNum;
    }

    public void setClientOrderNum(String clientOrderNum) {
        this.clientOrderNum = clientOrderNum;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientUnit() {
        return clientUnit;
    }

    public void setClientUnit(String clientUnit) {
        this.clientUnit = clientUnit;
    }

    public int getClientNum() {
        return clientNum;
    }

    public void setClientNum(int clientNum) {
        this.clientNum = clientNum;
    }

    public String getClientCompleteDate() {
        return clientCompleteDate;
    }

    public void setClientCompleteDate(String clientCompleteDate) {
        this.clientCompleteDate = clientCompleteDate;
    }

    public String getClientOrderName() {
        return clientOrderName;
    }

    public void setClientOrderName(String clientOrderName) {
        this.clientOrderName = clientOrderName;
    }
}
