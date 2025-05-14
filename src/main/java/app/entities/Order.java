package app.entities;

public class Order {
    private int orderId;
    private int userId;
    private int totalPrice;
    private String orderDate;
    private String orderStatus;
    private int width;
    private int length;
    User user;

    public Order(int orderId, int userId, int totalPrice, String orderDate, String orderStatus, int width, int length) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.width = width;
        this.length = length;
    }

    public Order(int totalPrice, String orderDate, String orderStatus, int width, int length) {
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.width = width;
        this.length = length;
    }

    public Order(int totalPrice, String orderDate, String orderStatus, int width, int length, User user, int userId, int orderId) {
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.width = width;
        this.length = length;
        this.user = user;
        this.orderId = orderId;
        this.userId =userId;
    }

    public Order(int orderId, int totalPrice, String orderStatus, int width, int length) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.width = width;
        this.length = length;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setUserId() {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", totalPrice=" + totalPrice +
                ", orderDate='" + orderDate + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", width=" + width +
                ", length=" + length +
                '}';
    }
}
