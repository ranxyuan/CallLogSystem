package com.ran.ssm.domain;

import java.util.List;

public class Order {
    private Integer id ;
    private String orderNo ;
    //建立关联关系
    private User user ;
    //添加item订单项集合
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order(Integer id, String orderNo, User user) {
        this.id = id;
        this.orderNo = orderNo;
        this.user = user;
    }

    public Order() {
    }
}
