package com.article.oa_article.bean.request;

import com.article.oa_article.module.create_order.ImageBO;

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
    private List<CompanyHonorsBean> companyHonors;
    private List<CompanyQualificationsBean> companyQualifications;
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

    public List<CompanyHonorsBean> getCompanyHonors() {
        return companyHonors;
    }

    public void setCompanyHonors(List<CompanyHonorsBean> companyHonors) {
        this.companyHonors = companyHonors;
    }

    public List<CompanyQualificationsBean> getCompanyQualifications() {
        return companyQualifications;
    }

    public void setCompanyQualifications(List<CompanyQualificationsBean> companyQualifications) {
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

    public static class CompanyHonorsBean {
        /**
         * honorId : 0
         * honorImage : [{"name":"string","url":"string"}]
         * honorName : string
         * issueDate : 2019-06-03T05:46:20.411Z
         * issueUnit : string
         */

        private int honorId;
        private String honorName;
        private String issueDate;
        private String issueUnit;
        private List<ImageBO> honorImage;

        public int getHonorId() {
            return honorId;
        }

        public void setHonorId(int honorId) {
            this.honorId = honorId;
        }

        public String getHonorName() {
            return honorName;
        }

        public void setHonorName(String honorName) {
            this.honorName = honorName;
        }

        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
        }

        public String getIssueUnit() {
            return issueUnit;
        }

        public void setIssueUnit(String issueUnit) {
            this.issueUnit = issueUnit;
        }

        public List<ImageBO> getHonorImage() {
            return honorImage;
        }

        public void setHonorImage(List<ImageBO> honorImage) {
            this.honorImage = honorImage;
        }


    }

    public static class CompanyQualificationsBean {
        /**
         * issueDate : 2019-06-03T05:46:20.411Z
         * issueUnit : string
         * qualificationId : 0
         * qualificationImage : [{"name":"string","url":"string"}]
         * qualificationName : string
         * qualificationNumber : string
         * remark : string
         * validDate : 2019-06-03T05:46:20.411Z
         */

        private String issueDate;
        private String issueUnit;
        private int qualificationId;
        private String qualificationName;
        private String qualificationNumber;
        private String remark;
        private String validDate;
        private List<ImageBO> qualificationImage;

        public String getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(String issueDate) {
            this.issueDate = issueDate;
        }

        public String getIssueUnit() {
            return issueUnit;
        }

        public void setIssueUnit(String issueUnit) {
            this.issueUnit = issueUnit;
        }

        public int getQualificationId() {
            return qualificationId;
        }

        public void setQualificationId(int qualificationId) {
            this.qualificationId = qualificationId;
        }

        public String getQualificationName() {
            return qualificationName;
        }

        public void setQualificationName(String qualificationName) {
            this.qualificationName = qualificationName;
        }

        public String getQualificationNumber() {
            return qualificationNumber;
        }

        public void setQualificationNumber(String qualificationNumber) {
            this.qualificationNumber = qualificationNumber;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getValidDate() {
            return validDate;
        }

        public void setValidDate(String validDate) {
            this.validDate = validDate;
        }

        public List<ImageBO> getQualificationImage() {
            return qualificationImage;
        }

        public void setQualificationImage(List<ImageBO> qualificationImage) {
            this.qualificationImage = qualificationImage;
        }


    }
}
