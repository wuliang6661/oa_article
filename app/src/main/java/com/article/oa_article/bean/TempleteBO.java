package com.article.oa_article.bean;

import java.io.Serializable;
import java.util.List;

public class TempleteBO implements Serializable {


    /**
     * name : 修改制衣模板going民工
     * id : 1
     * taskInfo : [{"taskType":0,"nickName":"李四","taskName":"制衣饰","id":1},{"taskType":1,"nickName":"钱二","taskName":"制帽饰","id":10}]
     * remarks : 沪小强制衣模板
     */

    private String name;
    private int id;
    private String remarks;
    private List<TaskInfoBean> taskInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<TaskInfoBean> getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(List<TaskInfoBean> taskInfo) {
        this.taskInfo = taskInfo;
    }

    public static class TaskInfoBean implements Serializable {
        /**
         * taskType : 0
         * nickName : 李四
         * taskName : 制衣饰
         * id : 1
         */

        private int taskType;
        private String nickName;
        private String taskName;
        private int id;
        private int userId;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getTaskType() {
            return taskType;
        }

        public void setTaskType(int taskType) {
            this.taskType = taskType;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
