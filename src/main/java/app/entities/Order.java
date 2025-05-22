package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int totalPrice;
    private String orderDate;
    private int width;
    private int length;
    private String orderStatus;
    private User user;

    public Order(int orderId, int totalPrice, String orderDate, int width, int length, String orderStatus, User user) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.width = width;
        this.length = length;
    }


    public Order(int orderId, int totalPrice, String orderStatus, int width, int length) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.width = width;
        this.length = length;
    }


    public Order(int orderId, String orderDate, String orderStatus, int width, int length, User user) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.user = user;
    }

    public Order(int width, int length) {
        this.width = width;
        this.length = length;
        this.user = user;
    }

    public Order(int totalPrice, String orderDate, String orderStatus, int width, int length, User user, int userId, int id) {
        totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.width = width;
        this.length = length;
        this.user = user;
        this.userId = userId;
        this.orderId = id;
    }



    // getter

    public int getOrderId() {
        return orderId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public User getUser() {
        return user;
    }


    //setter


    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", totalPrice=" + totalPrice +
                ", orderDate='" + orderDate + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", width=" + width +
                ", length=" + length +
                ", user=" + user +
                '}';
    }
}
