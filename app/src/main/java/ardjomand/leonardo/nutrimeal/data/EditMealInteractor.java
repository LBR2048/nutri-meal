package ardjomand.leonardo.nutrimeal.data;

import android.net.Uri;

import ardjomand.leonardo.nutrimeal.meals.Meal;

public interface EditMealInteractor {

    interface Interactor {

        void updateMeal(Meal meal);

        void updateMealImage(String key, Uri imageUri);

        void deleteMeal(String key);
    }

    interface Presenter {

        void onItemRead(Meal meal);
    }

}
