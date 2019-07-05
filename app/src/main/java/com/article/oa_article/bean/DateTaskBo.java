package com.article.oa_article.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/1311:11
 * desc   :
 * version: 1.0
 */
public class DateTaskBo {


    /**
     * companyOrderNum : 367
     * dayBetween : 2
     * taskName : 订单一的任务二号
     * id : 156
     * planCompleteDate : 1557504000000
     * companyOrderName : 公司订单
     */

    private String companyOrderNum;
    private int dayBetween;
    private String taskName;
    private int id;
    private long planCompleteDate;
    private String companyOrderName;
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getCompanyOrderNum() {
        return companyOrderNum;
    }

    public void setCompanyOrderNum(String companyOrderNum) {
        this.companyOrderNum = companyOrderNum;
    }

    public int getDayBetween() {
        return dayBetween;
    }

    public void setDayBetween(int dayBetween) {
        this.dayBetween = dayBetween;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPlanCompleteDate() {
        return planCompleteDate;
    }

    public void setPlanCompleteDate(long planCompleteDate) {
        this.planCompleteDate = planCompleteDate;
    }

    public String getCompanyOrderName() {
        return companyOrderName;
    }

    public void setCompanyOrderName(String companyOrderName) {
        this.companyOrderName = companyOrderName;
    }
}
