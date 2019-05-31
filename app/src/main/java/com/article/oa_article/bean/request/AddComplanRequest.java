package com.article.oa_article.bean.request;

import com.article.oa_article.module.create_order.ImageBO;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/3111:04
 * desc   :
 * version: 1.0
 */
public class AddComplanRequest {


    /**
     * companyDevices : [{"deviceId":0,"deviceName":"string","deviceNum":0}]
     * companyHonors : [{"honorId":0,"honorImage":[{"name":"string","url":"string"}],"honorName":"string","issueDate":"2019-05-31T02:57:09.380Z","issueUnit":"string"}]
     * companyInfo : {"adminNumber":0,"businessLicense":{"name":"string","url":"string"},"companyAddress":"string","companyEmail":"string","companyId":0,"companyImage":"string","companyName":"string","contactWay":"string","idBackImage":{"name":"string","url":"string"},"idFrontImage":{"name":"string","url":"string"},"ordinaryNumber":0,"plantArea":0,"plantImage":[{"name":"string","url":"string"}],"plantNature":0,"remark":"string","shortName":"string","status":0,"technicalNumber":0,"userId":0}
     * companyQualifications : [{"issueDate":"2019-05-31T02:57:09.380Z","issueUnit":"string","qualificationId":0,"qualificationImage":[{"name":"string","url":"string"}],"qualificationName":"string","qualificationNumber":"string","remark":"string","validDate":"2019-05-31T02:57:09.380Z"}]
     * token : string
     */

    private CompanyInfoBean companyInfo;
    private String token;
    private List<CompanyDevicesBean> companyDevices;
    private List<CompanyHonorsBean> companyHonors;
    private List<CompanyQualificationsBean> companyQualifications;

    public CompanyInfoBean getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfoBean companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<CompanyDevicesBean> getCompanyDevices() {
        return companyDevices;
    }

    public void setCompanyDevices(List<CompanyDevicesBean> companyDevices) {
        this.companyDevices = companyDevices;
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

    public static class CompanyInfoBean {
        /**
         * adminNumber : 0
         * businessLicense : {"name":"string","url":"string"}
         * companyAddress : string
         * companyEmail : string
         * companyId : 0
         * companyImage : string
         * companyName : string
         * contactWay : string
         * idBackImage : {"name":"string","url":"string"}
         * idFrontImage : {"name":"string","url":"string"}
         * ordinaryNumber : 0
         * plantArea : 0
         * plantImage : [{"name":"string","url":"string"}]
         * plantNature : 0
         * remark : string
         * shortName : string
         * status : 0
         * technicalNumber : 0
         * userId : 0
         */

        private int adminNumber;
        private ImageBO businessLicense;
        private String companyAddress;
        private String companyEmail;
        private int companyId;
        private String companyImage;
        private String companyName;
        private String contactWay;
        private ImageBO idBackImage;
        private ImageBO idFrontImage;
        private int ordinaryNumber;
        private int plantArea;
        private int plantNature;
        private String remark;
        private String shortName;
        private int status;
        private int technicalNumber;
        private int userId;
        private List<ImageBO> plantImage;

        public int getAdminNumber() {
            return adminNumber;
        }

        public void setAdminNumber(int adminNumber) {
            this.adminNumber = adminNumber;
        }

        public ImageBO getBusinessLicense() {
            return businessLicense;
        }

        public void setBusinessLicense(ImageBO businessLicense) {
            this.businessLicense = businessLicense;
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
        }

        public String getCompanyEmail() {
            return companyEmail;
        }

        public void setCompanyEmail(String companyEmail) {
            this.companyEmail = companyEmail;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getCompanyImage() {
            return companyImage;
        }

        public void setCompanyImage(String companyImage) {
            this.companyImage = companyImage;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getContactWay() {
            return contactWay;
        }

        public void setContactWay(String contactWay) {
            this.contactWay = contactWay;
        }

        public ImageBO getIdBackImage() {
            return idBackImage;
        }

        public void setIdBackImage(ImageBO idBackImage) {
            this.idBackImage = idBackImage;
        }

        public ImageBO getIdFrontImage() {
            return idFrontImage;
        }

        public void setIdFrontImage(ImageBO idFrontImage) {
            this.idFrontImage = idFrontImage;
        }

        public int getOrdinaryNumber() {
            return ordinaryNumber;
        }

        public void setOrdinaryNumber(int ordinaryNumber) {
            this.ordinaryNumber = ordinaryNumber;
        }

        public int getPlantArea() {
            return plantArea;
        }

        public void setPlantArea(int plantArea) {
            this.plantArea = plantArea;
        }

        public int getPlantNature() {
            return plantNature;
        }

        public void setPlantNature(int plantNature) {
            this.plantNature = plantNature;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTechnicalNumber() {
            return technicalNumber;
        }

        public void setTechnicalNumber(int technicalNumber) {
            this.technicalNumber = technicalNumber;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<ImageBO> getPlantImage() {
            return plantImage;
        }

        public void setPlantImage(List<ImageBO> plantImage) {
            this.plantImage = plantImage;
        }

    }

    public static class CompanyDevicesBean {
        /**
         * deviceId : 0
         * deviceName : string
         * deviceNum : 0
         */

        private int deviceId;
        private String deviceName;
        private int deviceNum;

        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public int getDeviceNum() {
            return deviceNum;
        }

        public void setDeviceNum(int deviceNum) {
            this.deviceNum = deviceNum;
        }
    }

    public static class CompanyHonorsBean {
        /**
         * honorId : 0
         * honorImage : [{"name":"string","url":"string"}]
         * honorName : string
         * issueDate : 2019-05-31T02:57:09.380Z
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
         * issueDate : 2019-05-31T02:57:09.380Z
         * issueUnit : string
         * qualificationId : 0
         * qualificationImage : [{"name":"string","url":"string"}]
         * qualificationName : string
         * qualificationNumber : string
         * remark : string
         * validDate : 2019-05-31T02:57:09.380Z
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
