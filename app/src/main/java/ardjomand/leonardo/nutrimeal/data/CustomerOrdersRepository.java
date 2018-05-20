package ardjomand.leonardo.nutrimeal.data;

import ardjomand.leonardo.nutrimeal.customerorders.Order;

public interface CustomerOrdersRepository {

    interface Repository {

        void subscribeForOrdersUpdates();

        void unsubscribeFromOrdersUpdates();
    }

    interface Presenter {

        void onOrderAdded(Order order);

        void onOrderChanged(Order order);

        void onOrderRemoved(String key);
    }
}
