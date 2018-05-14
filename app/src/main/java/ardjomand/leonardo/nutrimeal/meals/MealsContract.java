package ardjomand.leonardo.nutrimeal.meals;

import java.util.List;

interface MealsContract {

    interface View {

        void showMeals(List<Meal> meals);

        void addMeal(Meal meal);

        void showEmptyMeals();

        void showError();

        void goToEditMeal(Meal meal);
    }

    interface Presenter {

        void subscribeToMealsUpdates();

        void unsubscribeFromMealsUpdates();

        void addMealToCart(Meal meal);

        void editMeal(Meal meal);
    }
}
