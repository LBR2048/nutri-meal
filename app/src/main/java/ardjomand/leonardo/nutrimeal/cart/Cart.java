package ardjomand.leonardo.nutrimeal.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private long amount;
    private List<SelectedMeal> selectedMeals = new ArrayList<>(); // TODO use Set or other List implementation

    public Cart() {
    }

    public void addOrderedMeal(SelectedMeal selectedMeal) {
        selectedMeals.add(selectedMeal);
        amount += selectedMeal.getTotalPrice();
    }
}
