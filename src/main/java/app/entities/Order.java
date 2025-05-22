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
    private int userId;
    private User user;

    public Order(int orderId, int totalPrice, String orderDate, int width, int length, String orderStatus, int userId, User user) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.width = width;
        this.length = length;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.user = user;
    }

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

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String getOrderStatus() {
        return orderStatus;
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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
