package ardjomand.leonardo.nutrimeal.data;

import ardjomand.leonardo.nutrimeal.customerorders.CustomerOrder;

public interface CustomerOrdersRepository {

    interface Repository {

        void subscribeForOrdersUpdates();

        void unsubscribeFromOrdersUpdates();
    }

    interface Presenter {

        void onOrderAdded(CustomerOrder customerOrder);

        void onOrderChanged(CustomerOrder customerOrder);

        void onOrderRemoved(String key);
    }
}
