package ardjomand.leonardo.nutrimeal.cart;

import ardjomand.leonardo.nutrimeal.data.pojos.CartMeal;

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
