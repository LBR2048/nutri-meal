package ardjomand.leonardo.nutrimeal.meals;

import java.util.List;

interface MealContract {

    interface View {

        void showMeals(List<Meal> meals);

        void showEmptyMeals();

        void showError();
    }

    interface Presenter {

        void getMeals();

        void addMealToCart(Meal meal);
    }
}
