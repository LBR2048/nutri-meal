package ardjomand.leonardo.nutrimeal.orders;

import java.util.List;

import ardjomand.leonardo.nutrimeal.cart.CartMeal;

public class Order {

    private String key;
    private boolean delivered;
    private long amount;
    private long deliveryDate;
    private String customerKey;
//    private List<CartMeal> meals;

    public Order() {
    }

    public Order(boolean delivered, long amount, long deliveryDate, List<CartMeal> meals) {
        this.delivered = delivered;
        this.amount = amount;
        this.deliveryDate = deliveryDate;
//        this.meals = meals;
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

    public String getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }
//    public List<CartMeal> getMeals() {
//        return meals;
//    }
//
//    public void setMeals(List<CartMeal> meals) {
//        this.meals = meals;
//    }
}
