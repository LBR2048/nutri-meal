package ardjomand.leonardo.nutrimeal.cart;

public interface EditCartMealContract {

    interface View {

        void updateCartMeal(CartMeal meal);
    }

    interface Presenter {

        void subscribeForCartMealsUpdates(String key);

        void unsubscribeFromCartMealsUpdates();

        void editCartMeal(CartMeal cartMeal);
    }
}
