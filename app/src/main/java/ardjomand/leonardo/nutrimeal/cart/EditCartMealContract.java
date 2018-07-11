package ardjomand.leonardo.nutrimeal.cart;

interface EditCartMealContract {

    interface View {

        void updateCartMeal(CartMeal meal);
    }

    interface Presenter {

        void setView(View view);

        void subscribeForCartMealsUpdates(String key);

        void unsubscribeFromCartMealsUpdates();

        void editCartMeal(CartMeal cartMeal);
    }
}
