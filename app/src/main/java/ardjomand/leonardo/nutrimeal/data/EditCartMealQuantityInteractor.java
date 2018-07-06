package ardjomand.leonardo.nutrimeal.data;

import ardjomand.leonardo.nutrimeal.cart.CartMeal;

public interface EditCartMealQuantityInteractor {

    interface Interactor {

        void subscribeForCartMealUpdates(String key);

        void unsubscribeFromCartMealUpdates();

        void editCartMeal(CartMeal cartMeal);
    }

    interface Presenter {

        void onCartMealUpdated(CartMeal cartMeal);
    }
}
