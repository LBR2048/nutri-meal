package ardjomand.leonardo.nutrimeal.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private long amount;
    private List<CartMeal> meals = new ArrayList<>(); // TODO use Set or other List implementation

    public Cart() {
    }

    public void addOrderedMeal(CartMeal cartMeal) {
        meals.add(cartMeal);
        amount += cartMeal.getTotalPrice();
    }
}
