package com.article.oa_article.bean.request;

import com.article.oa_article.module.create_order.ImageBO;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/6/314:40
 * desc   :
 * version: 1.0
 */
public class UpdateZiYuanRequest {


    /**
     * adminNumber : 0
     * companyDevices : [{"companyId":0,"createDate":"2019-06-03T05:46:20.405Z","id":0,"name":"string","num":0,"remark":"string","updateDate":"2019-06-03T05:46:20.405Z"}]
     * companyId : 0
     * deleteIds : [0]
     * ordinaryNumber : 0
     * plantArea : 0
     * plantImage : [{"name":"string","url":"string"}]
     * plantNature : 0
     * technicalNumber : 0
     * token : string
     */

    private int adminNumber;
    private int companyId;
    private int ordinaryNumber;
    private double plantArea;
    private int plantNature;
    private int technicalNumber;
    private String token;
    private List<CompanyDevicesBean> companyDevices;
    private List<Integer> deleteIds;
    private List<ImageBO> plantImage;

    public int getAdminNumber() {
        return adminNumber;
    }

    public void setAdminNumber(int adminNumber) {
        this.adminNumber = adminNumber;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

    public int getTechnicalNumber() {
        return technicalNumber;
    }

    public void setTechnicalNumber(int technicalNumber) {
        this.technicalNumber = technicalNumber;
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

    public List<Integer> getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(List<Integer> deleteIds) {
        this.deleteIds = deleteIds;
    }

    public List<ImageBO> getPlantImage() {
        return plantImage;
    }

    public void setPlantImage(List<ImageBO> plantImage) {
        this.plantImage = plantImage;
    }

    public static class CompanyDevicesBean {
        /**
         * companyId : 0
         * createDate : 2019-06-03T05:46:20.405Z
         * id : 0
         * name : string
         * num : 0
         * remark : string
         * updateDate : 2019-06-03T05:46:20.405Z
         */

        private int companyId;
        private String createDate;
        private int id;
        private String name;
        private int num;
        private String remark;
        private String updateDate;

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
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

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }
    }


}
