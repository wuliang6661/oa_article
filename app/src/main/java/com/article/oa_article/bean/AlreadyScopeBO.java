package com.article.oa_article.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/1614:23
 * desc   :  已评价的任务信息
 * version: 1.0
 */
public class AlreadyScopeBO {


    /**
     * clientOrderNum : 367
     * clientName : 钱儿订单
     * serviceAttitudeScore : 1
     * nickName : 梦中人
     * dulDay : 0
     * productQualityScore : 1
     * clientOrderName : 公司订单
     * score : 1.0
     * priceRationalityScore : 1
     * logisticsScore : 1
     * taskName : 订单一的任务一号
     * punctualityScore : 1
     * taskId : 155
     */

    private String clientOrderNum;
    private String clientName;
    private int serviceAttitudeScore;
    private String nickName;
    private int dulDay;
    private int productQualityScore;
    private String clientOrderName;
    private double score;
    private int priceRationalityScore;
    private int logisticsScore;
    private String taskName;
    private int punctualityScore;
    private int taskId;

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

    public int getServiceAttitudeScore() {
        return serviceAttitudeScore;
    }

    public void setServiceAttitudeScore(int serviceAttitudeScore) {
        this.serviceAttitudeScore = serviceAttitudeScore;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getDulDay() {
        return dulDay;
    }

    public void setDulDay(int dulDay) {
        this.dulDay = dulDay;
    }

    public int getProductQualityScore() {
        return productQualityScore;
    }

    public void setProductQualityScore(int productQualityScore) {
        this.productQualityScore = productQualityScore;
    }

    public String getClientOrderName() {
        return clientOrderName;
    }

    public void setClientOrderName(String clientOrderName) {
        this.clientOrderName = clientOrderName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getPriceRationalityScore() {
        return priceRationalityScore;
    }

    public void setPriceRationalityScore(int priceRationalityScore) {
        this.priceRationalityScore = priceRationalityScore;
    }

    public int getLogisticsScore() {
        return logisticsScore;
    }

    public void setLogisticsScore(int logisticsScore) {
        this.logisticsScore = logisticsScore;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getPunctualityScore() {
        return punctualityScore;
    }

    public void setPunctualityScore(int punctualityScore) {
        this.punctualityScore = punctualityScore;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
