package com.pay.domain;

import java.util.Date;

public class User {
    private String id;
    //订单编号
    private String orderNum;
    //姓名
    private String name;
    //电话
    private String phone;
    //种类
    private String classItem;
    //金额
    private Float price;

    private Date createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClassItem() {
        return classItem;
    }

    public void setClassItem(String classItem) {
        this.classItem = classItem;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
