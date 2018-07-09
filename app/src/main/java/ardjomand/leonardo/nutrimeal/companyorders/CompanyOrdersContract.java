package ardjomand.leonardo.nutrimeal.companyorders;


interface CompanyOrdersContract {

    interface View {

        void addOrder(CompanyOrder companyOrder);

        void updateOrder(CompanyOrder companyOrder);

        void showEmptyOrder();

        void showError();
    }

    interface Presenter {

        void setView(View view);

        void subscribeToOrdersUpdates();

        void unsubscribeFromOrdersUpdates();
    }
}
