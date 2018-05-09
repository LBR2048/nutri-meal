package ardjomand.leonardo.nutrimeal.cart;

import ardjomand.leonardo.nutrimeal.meals.Meal;

interface CartContract {

    interface View {

        void addSelectedMeal(CartMeal meal);

        void showEmptyMeals();

        void showError();
    }

    interface Presenter {

        void subscribeToCartUpdates();

        void unsubscribeFromCartUpdates();

        void addMealToCart(Meal meal);

        void placeOrder();
    }
}
