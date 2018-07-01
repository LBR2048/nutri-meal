package ardjomand.leonardo.nutrimeal.data;

import java.util.List;

import ardjomand.leonardo.nutrimeal.customerorders.CustomerOrder;

public interface CustomerWidgetRepository {

    interface LoadCustomersOrdersCallback {
        void onComplete(List<CustomerOrder> customerOrders);
    }

    void loadCustomerOrders(LoadCustomersOrdersCallback loadCustomersOrdersCallback);
}
