package com.article.oa_article.view.mobanmanager;

import com.article.oa_article.bean.MuBanTaskBO;

import java.io.Serializable;
import java.util.List;

public class DataBean implements Serializable {


    private List<MuBanTaskBO> mubans;

    public List<MuBanTaskBO> getMubans() {
        return mubans;
    }

    public void setMubans(List<MuBanTaskBO> mubans) {
        this.mubans = mubans;
    }
}
