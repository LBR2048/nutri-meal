package ardjomand.leonardo.nutrimeal.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private long amount;
    private List<CartMeal> cartMeals = new ArrayList<>(); // TODO use Set or other List implementation

    public Cart() {
    }

    public void addOrderedMeal(CartMeal cartMeal) {
        cartMeals.add(cartMeal);
        amount += cartMeal.getTotalPrice();
    }
}
