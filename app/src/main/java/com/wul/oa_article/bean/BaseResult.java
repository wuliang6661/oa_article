package com.wul.oa_article.bean;

/**
 * Created by wuliang on 2017/3/27.
 * 所有返回的json数据的公有格式
 */

public class BaseResult<T> {

//    1）	status: 表成功和失败状态。1表成功，0表失败。
//            2）	errorMessage: 错误信息，当有错误发生时，此errorMessage包含有错误信息
//    3）	errorCode: 错误编码，当有错误发生时，此errorCode包含有错误编码
//    4）	data：返回数据

    private static int SURCESS = 200;

    private String status;

    private String msg;

    private int code;

    private T data;

    private PageBO pageInfo;

    public PageBO getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageBO pageInfo) {
        this.pageInfo = pageInfo;
    }

    public boolean surcess() {
        return code == 200;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
