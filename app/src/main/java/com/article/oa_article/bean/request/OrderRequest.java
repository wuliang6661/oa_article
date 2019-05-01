package com.article.oa_article.bean.request;

public class OrderRequest {

    public String menuType;

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

    private String companyId;
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
    private String userId;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
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


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
