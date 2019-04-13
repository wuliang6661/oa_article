package com.wul.oa_article.module.create_order;

public class PingLeiBO {

    public PingLeiBO(String name, String guige, String num, String danwei) {
        this.name = name;
        this.size = guige;
        this.num = num;
        this.unit = danwei;
    }


    public String createDate;
    public int id;
    public String name;
    public String num;
    public int orderId;
    public String remark;
    public String size;
    public String unit;
    public String updateDate;


}
