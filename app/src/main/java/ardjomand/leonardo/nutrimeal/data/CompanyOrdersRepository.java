package ardjomand.leonardo.nutrimeal.data;

import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrder;

public interface CompanyOrdersRepository {

    interface Repository {

        void subscribeForOrdersUpdates();

        void unsubscribeFromOrdersUpdates();
    }

    interface Presenter {

        void onOrderAdded(CompanyOrder companyOrder);

        void onOrderChanged(CompanyOrder companyOrder);

        void onOrderRemoved(String key);
    }
}
