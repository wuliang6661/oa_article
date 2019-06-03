package com.article.oa_article.bean.request;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/315:38
 * desc   :
 * version: 1.0
 */
public class UpdateShiliRequest {


    /**
     * companyHonors : [{"honorId":0,"honorImage":[{"name":"string","url":"string"}],"honorName":"string","issueDate":"2019-06-03T05:46:20.411Z","issueUnit":"string"}]
     * companyId : 0
     * companyQualifications : [{"issueDate":"2019-06-03T05:46:20.411Z","issueUnit":"string","qualificationId":0,"qualificationImage":[{"name":"string","url":"string"}],"qualificationName":"string","qualificationNumber":"string","remark":"string","validDate":"2019-06-03T05:46:20.411Z"}]
     * deleteHonorsIds : [0]
     * deleteQualificationsIds : [0]
     * token : string
     */

    private int companyId;
    private String token;
    private List<AddComplanRequest.CompanyHonorsBean> companyHonors;
    private List<AddComplanRequest.CompanyQualificationsBean> companyQualifications;
    private List<Integer> deleteHonorsIds;
    private List<Integer> deleteQualificationsIds;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<AddComplanRequest.CompanyHonorsBean> getCompanyHonors() {
        return companyHonors;
    }

    public void setCompanyHonors(List<AddComplanRequest.CompanyHonorsBean> companyHonors) {
        this.companyHonors = companyHonors;
    }

    public List<AddComplanRequest.CompanyQualificationsBean> getCompanyQualifications() {
        return companyQualifications;
    }

    public void setCompanyQualifications(List<AddComplanRequest.CompanyQualificationsBean> companyQualifications) {
        this.companyQualifications = companyQualifications;
    }

    public List<Integer> getDeleteHonorsIds() {
        return deleteHonorsIds;
    }

    public void setDeleteHonorsIds(List<Integer> deleteHonorsIds) {
        this.deleteHonorsIds = deleteHonorsIds;
    }

    public List<Integer> getDeleteQualificationsIds() {
        return deleteQualificationsIds;
    }

    public void setDeleteQualificationsIds(List<Integer> deleteQualificationsIds) {
        this.deleteQualificationsIds = deleteQualificationsIds;
    }


}
