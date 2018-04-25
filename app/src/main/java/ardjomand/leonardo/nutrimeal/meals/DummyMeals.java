package ardjomand.leonardo.nutrimeal.meals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample imagePath for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyMeals {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Meal> ITEMS = new ArrayList<Meal>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Meal> ITEM_MAP = new HashMap<String, Meal>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Meal item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getName(), item);
    }

    private static Meal createDummyItem(int position) {
        return new Meal("Meal " + String.valueOf(position), makeDetails(position), "Image Path " + position, position, true);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore description information here.");
        }
        return builder.toString();
    }

}
