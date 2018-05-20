package ardjomand.leonardo.nutrimeal.customerorders;

interface CustomerOrdersContract {

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
