package com.article.oa_article.bean.request;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/1617:44
 * desc   :
 * version: 1.0
 */
public class ScopeRequest {


    /**
     * logisticsScore : 0
     * priceRationalityScore : 0
     * productQualityScore : 0
     * punctualityScore : 0
     * serviceAttitudeScore : 0
     * taskId : 0
     * token : string
     * userId : 0
     */

    private int logisticsScore;
    private int priceRationalityScore;
    private int productQualityScore;
    private int punctualityScore;
    private int serviceAttitudeScore;
    private int taskId;
    private String token;
    private int userId;
    private int evaluateCompanyId;

    public int getEvaluateCompanyId() {
        return evaluateCompanyId;
    }

    public void setEvaluateCompanyId(int evaluateCompanyId) {
        this.evaluateCompanyId = evaluateCompanyId;
    }

    public int getLogisticsScore() {
        return logisticsScore;
    }

    public void setLogisticsScore(int logisticsScore) {
        this.logisticsScore = logisticsScore;
    }

    public int getPriceRationalityScore() {
        return priceRationalityScore;
    }

    public void setPriceRationalityScore(int priceRationalityScore) {
        this.priceRationalityScore = priceRationalityScore;
    }

    public int getProductQualityScore() {
        return productQualityScore;
    }

    public void setProductQualityScore(int productQualityScore) {
        this.productQualityScore = productQualityScore;
    }

    public int getPunctualityScore() {
        return punctualityScore;
    }

    public void setPunctualityScore(int punctualityScore) {
        this.punctualityScore = punctualityScore;
    }

    public int getServiceAttitudeScore() {
        return serviceAttitudeScore;
    }

    public void setServiceAttitudeScore(int serviceAttitudeScore) {
        this.serviceAttitudeScore = serviceAttitudeScore;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
