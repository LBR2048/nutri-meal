package ardjomand.leonardo.nutrimeal.orders;

import ardjomand.leonardo.nutrimeal.customerorders.Order;

interface OrdersContract {

    interface View {

        void addOrder(Order order);

        void updateOrder(Order order);

        void showEmptyOrder();

        void showError();
    }

    interface Presenter {

        void subscribeToOrdersUpdates();

        void unsubscribeFromOrdersUpdates();
    }
}
