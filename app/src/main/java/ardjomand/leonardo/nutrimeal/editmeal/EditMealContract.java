package ardjomand.leonardo.nutrimeal.editmeal;

import android.net.Uri;

import ardjomand.leonardo.nutrimeal.meals.Meal;

interface EditMealContract {

    interface View {

        void showMeal(Meal meal);

        void showMealImage(String imagePath);

        void showError();
    }

    interface Presenter {

        void subscribeForMealUpdates(String key);

        void unsubscribeFromMealUpdates();

        void updateMeal(Meal meal);

        void updateMealImage(String key, Uri imageUri);
    }
}
