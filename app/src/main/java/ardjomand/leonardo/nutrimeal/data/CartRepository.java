package ardjomand.leonardo.nutrimeal.data;

import ardjomand.leonardo.nutrimeal.cart.SelectedMeal;

public interface CartRepository {

    interface Repository {

        void subscribeForCartUpdates();

        void unsubscribeFromCartUpdates();
    }

    interface Presenter {

        void onSelectedMealAdded(SelectedMeal selectedMeal);

        void onSelectedMealChanged(SelectedMeal selectedMeal);

        void onSelectedMealRemoved(String key);
    }
}
