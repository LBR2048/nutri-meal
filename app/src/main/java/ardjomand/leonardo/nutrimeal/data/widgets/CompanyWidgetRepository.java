package ardjomand.leonardo.nutrimeal.data.widgets;

import java.util.List;

import ardjomand.leonardo.nutrimeal.data.pojos.CompanyOrder;

public interface CompanyWidgetRepository {

    void loadCompanyOrders(LoadCompanyOrdersCallback loadCompanyOrdersCallback);

    interface LoadCompanyOrdersCallback {
        void onComplete(List<CompanyOrder> companyOrders);
        void onUserLoggedOut();
    }
}
