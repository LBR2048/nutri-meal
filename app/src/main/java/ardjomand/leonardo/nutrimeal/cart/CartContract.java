package ardjomand.leonardo.nutrimeal.cart;

import java.util.List;

import ardjomand.leonardo.nutrimeal.meals.Meal;

interface CartContract {

    interface View {

        void addSelectedMeal(SelectedMeal meal);

        void showEmptyMeals();

        void showError();
    }

    interface Presenter {

        void getSelectedMeals();

        void addMealToCart(Meal meal);
    }
}
