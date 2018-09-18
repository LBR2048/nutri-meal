package ardjomand.leonardo.nutrimeal.cart;

import ardjomand.leonardo.nutrimeal.data.pojos.CartMeal;
import ardjomand.leonardo.nutrimeal.data.pojos.Meal;

interface CartContract {

    interface View {

        void addCartMeal(CartMeal meal);

        void updateCartMeal(CartMeal meal);

        void showEmptyMeals();

        void showError();
    }

    interface Presenter {

        void setView(View view);

        void subscribeToCartUpdates();

        void unsubscribeFromCartUpdates();

        void addMealToCart(Meal meal);

        void placeOrder();
    }
}
