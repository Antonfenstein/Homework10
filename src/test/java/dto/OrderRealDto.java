package dto;

public class OrderRealDto {
    private String customerName, customerPhone, comment;
    private String status;
    private long id;
    private int courierId;

    public OrderRealDto(String customerName, String customerPhone, String comment) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.comment = comment;
        this.status = "OPEN";
    }

    public OrderRealDto() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }

    public int getCourierId() {
        return courierId;
    }
}
