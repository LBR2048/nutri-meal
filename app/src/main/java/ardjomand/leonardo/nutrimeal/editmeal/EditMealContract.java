package ardjomand.leonardo.nutrimeal.editmeal;

import android.net.Uri;

import ardjomand.leonardo.nutrimeal.data.pojos.Meal;

interface EditMealContract {

    interface View {

        void showMeal(Meal meal);

        void showMealImage(String imagePath);

        void showAddMealImageIcon();

        void showError();
    }

    interface Presenter {

        void setView(View view);

        void subscribeForMealUpdates(String key);

        void unsubscribeFromMealUpdates();

        void updateMeal(Meal meal);

        void updateMealImage(String key, Uri imageUri);

        void deleteMeal(String key);
    }
}
