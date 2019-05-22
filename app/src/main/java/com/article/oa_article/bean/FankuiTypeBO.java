package com.article.oa_article.bean;

import java.io.Serializable;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2213:17
 * desc   :
 * version: 1.0
 */
public class FankuiTypeBO implements Serializable {


    /**
     * code : 1
     * createDate : 1558427222000
     * id : 1
     * name : 反馈类型一
     * updateDate : 1558427225000
     */

    private int code;
    private long createDate;
    private int id;
    private String name;
    private long updateDate;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }
}
