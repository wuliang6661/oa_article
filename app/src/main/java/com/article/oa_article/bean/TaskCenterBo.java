package com.article.oa_article.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/1516:12
 * desc   :
 * version: 1.0
 */
public class TaskCenterBo {


    /**
     * totle : 5
     * overdue : 2
     * overdueRate : 40%
     * userName :
     * departName :
     */

    private int totle;
    private int overdue;
    private String overdueRate;
    private String userName;
    private String departName;

    public int getTotle() {
        return totle;
    }

    public void setTotle(int totle) {
        this.totle = totle;
    }

    public int getOverdue() {
        return overdue;
    }

    public void setOverdue(int overdue) {
        this.overdue = overdue;
    }

    public String getOverdueRate() {
        return overdueRate;
    }

    public void setOverdueRate(String overdueRate) {
        this.overdueRate = overdueRate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }
}
