package com.article.oa_article.bean;

public class OrderNumBO {


    /**
     * all : 258
     * cancel : 10
     * ongoing : 243
     * complete : 5
     */

    private int all;
    private int cancel;
    private int ongoing;
    private int complete;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getCancel() {
        return cancel;
    }

    public void setCancel(int cancel) {
        this.cancel = cancel;
    }

    public int getOngoing() {
        return ongoing;
    }

    public void setOngoing(int ongoing) {
        this.ongoing = ongoing;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }
}
