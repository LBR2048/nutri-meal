package ardjomand.leonardo.nutrimeal.cart;

import java.util.ArrayList;
import java.util.List;

import ardjomand.leonardo.nutrimeal.data.KeyClass;

class Cart extends KeyClass{

    private long amount;
    private List<CartMeal> meals = new ArrayList<>(); // TODO use Set or other List implementation

    public Cart() {
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public List<CartMeal> getMeals() {
        return meals;
    }

    public void setMeals(List<CartMeal> meals) {
        this.meals = meals;
    }

    public void addOrderedMeal(CartMeal cartMeal) {
        meals.add(cartMeal);
        amount += cartMeal.getTotalPrice();
    }
}
