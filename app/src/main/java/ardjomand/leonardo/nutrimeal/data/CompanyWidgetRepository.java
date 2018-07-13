package ardjomand.leonardo.nutrimeal.data;

import java.util.List;

import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrder;

public interface CompanyWidgetRepository {

    void loadCompanyOrders(LoadCompanyOrdersCallback loadCompanyOrdersCallback);

    interface LoadCompanyOrdersCallback {
        void onComplete(List<CompanyOrder> companyOrders);
        void onUserLoggedOut();
    }
}
