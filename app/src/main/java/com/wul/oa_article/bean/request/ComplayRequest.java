package com.wul.oa_article.bean.request;

public class ComplayRequest {


    /**
     * companyId : 0
     * creatEndDate : 2019-03-28T12:09:02.117Z
     * creatStartDate : 2019-03-28T12:09:02.117Z
     * endDate : 2019-03-28T12:09:02.117Z
     * keyWord : string
     * pageNum : 0
     * pageSize : 0
     * startDate : 2019-03-28T12:09:02.117Z
     * taskType : 0
     * token : string
     * type : 0
     */

    private String companyId;
    private String creatEndDate;
    private String creatStartDate;
    private String endDate;
    private String keyWord;
    private int pageNum;
    private int pageSize;
    private String startDate;
    private String taskType;
    private String token;
    private String type;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
