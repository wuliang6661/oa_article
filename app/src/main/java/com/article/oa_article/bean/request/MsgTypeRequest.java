package com.article.oa_article.bean.request;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2718:24
 * desc   :
 * version: 1.0
 */
public class MsgTypeRequest {


    /**
     * ids : string
     * readStatus : 0
     * token : string
     */

    private String ids;
    private int readStatus;
    private String token;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
