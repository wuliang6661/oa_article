package com.wul.oa_article.bean;

import java.util.List;

public class TaskDetails {


    /**
     * taskHistory : [{"num":100,"id":1,"createDate":1551751200000},{"num":-33,"id":2,"createDate":1551751200000}]
     * taskInfo : {"unit":"个","orderId":1,"nickName":"张三","num":"20000","taskName":"造衣衣","remark":"","planCompleteDate":1551369600000,"id":1,"parentId":0,"createName":"༄梦中人࿐","status":0}
     */

    private PenPaiTaskBO taskInfo;
    private List<TaskHistoryBean> taskHistory;

    public PenPaiTaskBO getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(PenPaiTaskBO taskInfo) {
        this.taskInfo = taskInfo;
    }

    public List<TaskHistoryBean> getTaskHistory() {
        return taskHistory;
    }

    public void setTaskHistory(List<TaskHistoryBean> taskHistory) {
        this.taskHistory = taskHistory;
    }


    public static class TaskHistoryBean {
        /**
         * num : 100
         * id : 1
         * createDate : 1551751200000
         */

        private int num;
        private int id;
        private long createDate;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }
    }
}
