package ardjomand.leonardo.nutrimeal.customerorders;

interface CustomerOrdersContract {

    interface View {

        void addOrder(CustomerOrder customerOrder);

        void updateOrder(CustomerOrder customerOrder);

        void showEmptyOrder();

        void showError();
    }

    interface Presenter {

        void setView(View view);

        void subscribeToOrdersUpdates();

        void unsubscribeFromOrdersUpdates();
    }
}
