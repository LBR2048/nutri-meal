package ardjomand.leonardo.nutrimeal.editmeal;

import android.net.Uri;

import ardjomand.leonardo.nutrimeal.ItemObserverPresenter;
import ardjomand.leonardo.nutrimeal.ItemObserverView;
import ardjomand.leonardo.nutrimeal.data.pojos.Meal;

interface EditMealContract {

    interface View<T> extends ItemObserverView<T> {

        void showMealImage(String imagePath);

        void showAddMealImageIcon();
    }

    interface Presenter extends ItemObserverPresenter {

        void setView(View view);

        void updateMeal(Meal meal);

        void updateMealImage(String key, Uri imageUri);

        void deleteMeal(String key);
    }
}
