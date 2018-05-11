package ardjomand.leonardo.nutrimeal.cart;

public class CartMeal { // TODO extend from Meal
    private String name;
    private String description;
    private String imagePath;
    private long unitPrice;
    private int quantity;
    private long totalPrice;

    public CartMeal() {
    }

    public CartMeal(String name, String description, String imagePath, long unitPrice, int quantity) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = unitPrice * quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = unitPrice * quantity;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return imagePath;
    }
}
