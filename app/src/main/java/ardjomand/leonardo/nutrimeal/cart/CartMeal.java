package ardjomand.leonardo.nutrimeal.cart;

public class CartMeal { // TODO extend from Meal

    private String key;
    private String name;
    private String description;
    private String imagePath;
    private long unitPrice;
    private int quantity;
    private long totalPrice;

    public CartMeal() {
    }

    public CartMeal(String key, String name, String description, String imagePath, long unitPrice, int quantity) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = unitPrice * quantity;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public void increaseQuantity() {
        setQuantity(quantity + 1);
    }

    public void decreaseQuantity() {
        if (quantity > 1) setQuantity(quantity - 1);
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return imagePath;
    }
}
