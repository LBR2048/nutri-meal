package ardjomand.leonardo.nutrimeal.customerorders;

import java.util.List;

import ardjomand.leonardo.nutrimeal.cart.CartMeal;

public class CustomerOrder {

    private String key;
    private boolean delivered;
    private long amount;
    private long deliveryDate;

    public CustomerOrder() {
    }

    public CustomerOrder(boolean delivered, long amount, long deliveryDate, List<CartMeal> meals) {
        this.delivered = delivered;
        this.amount = amount;
        this.deliveryDate = deliveryDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
