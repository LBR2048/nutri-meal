package ardjomand.leonardo.nutrimeal.editmeal;

import ardjomand.leonardo.nutrimeal.meals.Meal;

interface EditMealContract {

    interface View {

        void showMeal(Meal meal);

        void showError();
    }

    interface Presenter {

        void getMeal(String key);

        void updateMeal(Meal meal);
    }
}
