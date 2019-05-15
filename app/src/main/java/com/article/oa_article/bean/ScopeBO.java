package com.article.oa_article.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/1516:53
 * desc   :
 * version: 1.0
 */
public class ScopeBO {


    /**
     * score : 1.8
     * clientOrderNum : 201901300001
     * clientName : 鑫鑫服装店
     * serviceAttitudeScore : 1
     * priceRationalityScore : 1
     * logisticsScore : 1
     * productQualityScore : 5
     * punctualityScore : 1
     * clientOrderName : 好彩运服装采购订单
     */

    private double score;
    private String clientOrderNum;
    private String clientName;
    private int serviceAttitudeScore;
    private int priceRationalityScore;
    private int logisticsScore;
    private int productQualityScore;
    private int punctualityScore;
    private String clientOrderName;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

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

    public String getClientOrderName() {
        return clientOrderName;
    }

    public void setClientOrderName(String clientOrderName) {
        this.clientOrderName = clientOrderName;
    }
}
