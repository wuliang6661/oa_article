package com.article.oa_article.bean;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/2116:24
 * desc   :
 * version: 1.0
 */
public class MsgBO {


    /**
     * readStatus : 0
     * image : http://xiaoqiangzhizao.oss-cn-hangzhou.aliyuncs.com/xqzz/2019052017/201905201715538602469.jpg
     * nickName : 哈哈哈发多少
     * orderNum :
     * taskLevel :
     * content : 申请成为好友
     * messageType : 0
     * noReadCounts : 3
     * id : 8
     * objectId : 6
     * taskStatus :
     * createDate : 1553067650000
     * orderName :
     */

    private int readStatus;
    private String image;
    private String nickName;
    private String orderNum;
    private String taskLevel;
    private String content;
    private int messageType;
    private int noReadCounts;
    private int id;
    private int objectId;
    private String taskStatus;
    private long createDate;
    private String orderName;
    private int page;
    private String friendTime;

    public String getFriendTime() {
        return friendTime;
    }

    public void setFriendTime(String friendTime) {
        this.friendTime = friendTime;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(String taskLevel) {
        this.taskLevel = taskLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getNoReadCounts() {
        return noReadCounts;
    }

    public void setNoReadCounts(int noReadCounts) {
        this.noReadCounts = noReadCounts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
}
