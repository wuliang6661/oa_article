package com.article.oa_article.bean;

import com.article.oa_article.module.create_order.ImageBO;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2215:29
 * desc   :
 * version: 1.0
 */
public class ComplanBO {


    /**
     * companyInfos : {"adminNumber":0,"businessLicense":{"name":"图片一","url":"http://xiaoqiangzhizao.oss-cn-hangzhou.aliyuncs.com/xqzz/2019041519/201904151950519227775.png"},"companyAddress":"浙江杭州西湖区","companyEmail":"www.baidu.com","companyId":1,"companyImage":"string","companyName":"小强浙江总公司","contactWay":"小强浙江总公司","idBackImage":{"name":"IMG_1683","url":"http://xiaoqiangzhizao.oss-cn-hangzhou.aliyuncs.com/xqzz/2019041519/201904151950519227775.png"},"idFrontImage":{"name":"IMG_1683","url":"http://xiaoqiangzhizao.oss-cn-hangzhou.aliyuncs.com/xqzz/2019041519/201904151950519227775.png"},"ordinaryNumber":10,"plantArea":0,"plantImage":[{"name":"string","url":"string"}],"plantNature":0,"remark":"","shortName":"小强","technicalNumber":0,"userId":5}
     * companyQualifications : [{"issueDate":1558368000000,"issueUnit":"string","qualificationImage":[{"name":"string","url":"string"}],"qualificationName":"string","qualificationNumber":"string","remark":"string","validDate":1550851200000}]
     * devices : [{"companyId":1,"createDate":1550196000000,"id":1,"name":"string","num":10,"remark":"string","updateDate":1558425998000},{"companyId":1,"createDate":1550914292000,"id":2,"name":"印花设备","num":300,"updateDate":1558332935000}]
     * companyHonors : [{"honorId":1,"honorImage":[{"name":"string","url":"string"}],"honorName":"string","issueDate":1558368000000,"issueUnit":"string"},{"honorId":2,"honorImage":[{"name":"IMG_1683","url":"http://xiaoqiangzhizao.oss-cn-hangzhou.aliyuncs.com/xqzz/2019041519/201904151950519227775.png"}],"honorName":"浙小强大批量生产荣耀","issueDate":1550246400000,"issueUnit":"大批量颁发"}]
     */

    private CompanyInfosBean companyInfos;    //公司信息
    private List<CompanyQualificationsBean> companyQualifications;   //资质
    private List<DevicesBean> devices;    //设备集合
    private List<CompanyHonorsBean> companyHonors;    //荣誉

    public CompanyInfosBean getCompanyInfos() {
        return companyInfos;
    }

    public void setCompanyInfos(CompanyInfosBean companyInfos) {
        this.companyInfos = companyInfos;
    }

    public List<CompanyQualificationsBean> getCompanyQualifications() {
        return companyQualifications;
    }

    public void setCompanyQualifications(List<CompanyQualificationsBean> companyQualifications) {
        this.companyQualifications = companyQualifications;
    }

    public List<DevicesBean> getDevices() {
        return devices;
    }

    public void setDevices(List<DevicesBean> devices) {
        this.devices = devices;
    }

    public List<CompanyHonorsBean> getCompanyHonors() {
        return companyHonors;
    }

    public void setCompanyHonors(List<CompanyHonorsBean> companyHonors) {
        this.companyHonors = companyHonors;
    }

    public static class CompanyInfosBean {
        /**
         * adminNumber : 0
         * businessLicense : {"name":"图片一","url":"http://xiaoqiangzhizao.oss-cn-hangzhou.aliyuncs.com/xqzz/2019041519/201904151950519227775.png"}
         * companyAddress : 浙江杭州西湖区
         * companyEmail : www.baidu.com
         * companyId : 1
         * companyImage : string
         * companyName : 小强浙江总公司
         * contactWay : 小强浙江总公司
         * idBackImage : {"name":"IMG_1683","url":"http://xiaoqiangzhizao.oss-cn-hangzhou.aliyuncs.com/xqzz/2019041519/201904151950519227775.png"}
         * idFrontImage : {"name":"IMG_1683","url":"http://xiaoqiangzhizao.oss-cn-hangzhou.aliyuncs.com/xqzz/2019041519/201904151950519227775.png"}
         * ordinaryNumber : 10
         * plantArea : 0.0
         * plantImage : [{"name":"string","url":"string"}]
         * plantNature : 0
         * remark :
         * shortName : 小强
         * technicalNumber : 0
         * userId : 5
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
        private double plantArea;
        private int plantNature;
        private String remark;
        private String shortName;
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

        public double getPlantArea() {
            return plantArea;
        }

        public void setPlantArea(double plantArea) {
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

    public static class CompanyQualificationsBean {
        /**
         * issueDate : 1558368000000
         * issueUnit : string
         * qualificationImage : [{"name":"string","url":"string"}]
         * qualificationName : string
         * qualificationNumber : string
         * remark : string
         * validDate : 1550851200000
         */

        private long issueDate;
        private String issueUnit;
        private String qualificationName;
        private String qualificationNumber;
        private String remark;
        private long validDate;
        private List<ImageBO> qualificationImage;
        private int qualificationId;


        public int getQualificationId() {
            return qualificationId;
        }

        public void setQualificationId(int qualificationId) {
            this.qualificationId = qualificationId;
        }

        public long getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(long issueDate) {
            this.issueDate = issueDate;
        }

        public String getIssueUnit() {
            return issueUnit;
        }

        public void setIssueUnit(String issueUnit) {
            this.issueUnit = issueUnit;
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

        public long getValidDate() {
            return validDate;
        }

        public void setValidDate(long validDate) {
            this.validDate = validDate;
        }

        public List<ImageBO> getQualificationImage() {
            return qualificationImage;
        }

        public void setQualificationImage(List<ImageBO> qualificationImage) {
            this.qualificationImage = qualificationImage;
        }

    }

    public static class DevicesBean {
        /**
         * companyId : 1
         * createDate : 1550196000000
         * id : 1
         * name : string
         * num : 10
         * remark : string
         * updateDate : 1558425998000
         */

        private int companyId;
        private long createDate;
        private int id;
        private String name;
        private int num;
        private String remark;
        private long updateDate;

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public long getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(long updateDate) {
            this.updateDate = updateDate;
        }
    }

    public static class CompanyHonorsBean {
        /**
         * honorId : 1
         * honorImage : [{"name":"string","url":"string"}]
         * honorName : string
         * issueDate : 1558368000000
         * issueUnit : string
         */

        private int honorId;
        private String honorName;
        private long issueDate;
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

        public long getIssueDate() {
            return issueDate;
        }

        public void setIssueDate(long issueDate) {
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
}
