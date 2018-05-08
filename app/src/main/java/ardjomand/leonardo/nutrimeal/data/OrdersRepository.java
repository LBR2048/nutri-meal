package ardjomand.leonardo.nutrimeal.data;

import ardjomand.leonardo.nutrimeal.orders.Order;

public interface OrdersRepository {

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
