package ardjomand.leonardo.nutrimeal.cart;

interface EditCartMealContract {

    interface View {

        void updateCartMeal(CartMeal meal);
    }

    interface Presenter {

        void setView(View view);

        void subscribe(String key);

        void unsubscribe();

        void increaseQuantity();

        void decreaseQuantity();
    }
}
