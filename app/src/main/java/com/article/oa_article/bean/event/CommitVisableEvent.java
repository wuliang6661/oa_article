package com.article.oa_article.bean.event;

public class CommitVisableEvent {


    public CommitVisableEvent(int visiable) {
        this.visiable = visiable;
    }

    /**
     * 0 不显示   1显示
     */
    public int visiable;
}
