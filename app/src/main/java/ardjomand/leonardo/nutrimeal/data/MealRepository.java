package ardjomand.leonardo.nutrimeal.data;

import ardjomand.leonardo.nutrimeal.meals.Meal;

public interface MealRepository {

    interface Repository {

        void subscribeForMealUpdates();

        void unsubscribeFromMealUpdates();
    }

    interface Presenter {

        void onMealAdded(Meal meal);

        void onMealChanged(Meal meal);

        void onMealRemoved(String key);
    }
}
