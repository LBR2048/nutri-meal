package ardjomand.leonardo.nutrimeal.meals;

import java.util.List;

interface MealsContract {

    interface View {

        void showMeals(List<Meal> meals);

        void addMeal(Meal meal);

        void updateMeal(Meal meal);

        void showEmptyMeals();

        void showError();

        void goToEditMeal(String key);

        void goToEditCartMealQuantity(String key);
    }

    interface Presenter {

        void subscribeToMealsUpdates();

        void unsubscribeFromMealsUpdates();

        void addMealToCart(Meal meal);

        void editMeal(String key);

        void editCartMealQuantity(String key);

        void createNewMeal();
    }
}
