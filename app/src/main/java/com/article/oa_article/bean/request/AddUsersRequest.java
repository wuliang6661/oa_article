package com.article.oa_article.bean.request;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2414:10
 * desc   :
 * version: 1.0
 */
public class AddUsersRequest {


    /**
     * token : string
     * users : [{"companyId":0,"departId":0,"labelId":0,"phone":"string","token":"string"}]
     */

    private String token;
    private List<AddUserRequest> users;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<AddUserRequest> getUsers() {
        return users;
    }

    public void setUsers(List<AddUserRequest> users) {
        this.users = users;
    }

}
