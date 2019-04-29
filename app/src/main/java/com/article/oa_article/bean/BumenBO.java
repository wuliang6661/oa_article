package com.article.oa_article.bean;

import java.util.List;

public class BumenBO {


    /**
     * depart : 常用联系人
     * user : [{"phone":"12312345678","name":"李四","id":4,"depart":"鸡本部"},{"phone":"13546987125","name":"张三","id":3,"depart":"鸡本部"},{"phone":"12112345678","name":"诸葛亮","id":1,"depart":""},{"phone":"18368163483","name":"༄梦中人࿐","id":5,"depart":""}]
     */

    private String depart;
    private List<PersonBO> user;
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public List<PersonBO> getUser() {
        return user;
    }

    public void setUser(List<PersonBO> user) {
        this.user = user;
    }


}
