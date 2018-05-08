package ardjomand.leonardo.nutrimeal.cart;

import ardjomand.leonardo.nutrimeal.meals.Meal;

interface CartContract {

    interface View {

        void addSelectedMeal(CartMeal meal);

        void showEmptyMeals();

        void showError();
    }

    interface Presenter {

        void getSelectedMeals();

        void addMealToCart(Meal meal);
    }
}
