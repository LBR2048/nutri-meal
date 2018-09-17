package ardjomand.leonardo.nutrimeal.customerorders;

import ardjomand.leonardo.nutrimeal.data.pojos.CustomerOrder;

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
