package ardjomand.leonardo.nutrimeal.meals;

public class Meal {
    private String key;
    private String name;
    private String description;
    private String imagePath;
    private long unitPrice;
    private boolean available;

    @SuppressWarnings("unused")
    public Meal() {
    }

    public Meal(String key, String name, String description, String imagePath, long unitPrice, boolean available) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.unitPrice = unitPrice;
        this.available = available;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "name='" + name + '\'' +
                '}';
    }
}
