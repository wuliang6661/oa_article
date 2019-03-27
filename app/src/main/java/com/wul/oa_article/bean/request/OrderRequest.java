package com.wul.oa_article.bean.request;

public class OrderRequest {


    /**
     * companyId : 0
     * creatEndDate : 2019-03-27T04:46:17.320Z
     * creatStartDate : 2019-03-27T04:46:17.320Z
     * days : 0
     * endDate : 2019-03-27T04:46:17.320Z
     * keyWord : string
     * pageNum : 0
     * pageSize : 0
     * startDate : 2019-03-27T04:46:17.320Z
     * taskType : 0
     * token : string
     * type : 0
     * userId : 0
     */

    private int companyId;
    private String creatEndDate;
    private String creatStartDate;
    private String days;
    private String endDate;
    private String keyWord;
    private int pageNum;
    private int pageSize;
    private String startDate;
    private String taskType;
    private String token;
    private String type;
    private int userId;


    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCreatEndDate() {
        return creatEndDate;
    }

    public void setCreatEndDate(String creatEndDate) {
        this.creatEndDate = creatEndDate;
    }

    public String getCreatStartDate() {
        return creatStartDate;
    }

    public void setCreatStartDate(String creatStartDate) {
        this.creatStartDate = creatStartDate;
    }


    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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
