package ardjomand.leonardo.nutrimeal.meals;

import ardjomand.leonardo.nutrimeal.ObserverPresenter;
import ardjomand.leonardo.nutrimeal.ObserverView;
import ardjomand.leonardo.nutrimeal.data.pojos.Meal;

interface MealsContract {

    interface View<T> extends ObserverView<T> {

        void goToEditMeal(String key);

        void goToEditCartMealQuantity(String key);
    }

    interface Presenter extends ObserverPresenter {

        void setView(View view);

        void addMealToCart(Meal meal);

        void editMeal(String key);

        void editCartMealQuantity(String key);

        void createNewMeal();
    }
}
