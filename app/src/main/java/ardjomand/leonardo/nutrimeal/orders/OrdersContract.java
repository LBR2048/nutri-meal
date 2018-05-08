package ardjomand.leonardo.nutrimeal.orders;

import java.util.List;

import ardjomand.leonardo.nutrimeal.meals.Meal;

interface OrdersContract {

    interface View {

        void addOrder(Order order);

        void showEmptyOrder();

        void showError();
    }

    interface Presenter {

        void getOrders();
    }
}
