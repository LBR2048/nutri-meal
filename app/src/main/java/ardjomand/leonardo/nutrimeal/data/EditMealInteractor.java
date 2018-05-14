package ardjomand.leonardo.nutrimeal.data;

import ardjomand.leonardo.nutrimeal.meals.Meal;

public interface EditMealInteractor {

    interface Interactor {

        void getMeal(String key);

        void updateMeal(Meal meal);
    }

    interface Presenter {

        void showMeal(Meal meal);
    }

}
