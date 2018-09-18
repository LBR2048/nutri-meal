package ardjomand.leonardo.nutrimeal.data.widgets;

import java.util.List;

import ardjomand.leonardo.nutrimeal.data.pojos.CustomerOrder;

public interface CustomerWidgetRepository {

    interface LoadCustomersOrdersCallback {
        void onComplete(List<CustomerOrder> customerOrders);
        void onUserLoggedOut();
    }

    void loadCustomerOrders(LoadCustomersOrdersCallback loadCustomersOrdersCallback);
}
