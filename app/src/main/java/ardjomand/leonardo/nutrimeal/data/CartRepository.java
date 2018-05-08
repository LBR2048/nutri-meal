package ardjomand.leonardo.nutrimeal.data;

import ardjomand.leonardo.nutrimeal.cart.CartMeal;

public interface CartRepository {

    interface Repository {

        void subscribeForCartUpdates();

        void unsubscribeFromCartUpdates();
    }

    interface Presenter {

        void onSelectedMealAdded(CartMeal cartMeal);

        void onSelectedMealChanged(CartMeal cartMeal);

        void onSelectedMealRemoved(String key);
    }
}
