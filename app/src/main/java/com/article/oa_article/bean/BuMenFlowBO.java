package com.article.oa_article.bean;

import java.io.Serializable;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/1419:02
 * desc   :
 * version: 1.0
 */
public class BuMenFlowBO implements Serializable {


    /**
     * name : 行政部
     * id : 3
     * users : 2
     */

    private String name;
    private String id;
    private int users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }
}
