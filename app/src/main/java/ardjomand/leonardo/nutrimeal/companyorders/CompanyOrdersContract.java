package ardjomand.leonardo.nutrimeal.companyorders;

import ardjomand.leonardo.nutrimeal.auth.User;

interface CompanyOrdersContract {

    interface View {

        void addUser(User user);

        void updateUser(User user);

        void showEmptyUser();

        void showError();
    }

    interface Presenter {

        void subscribeToUsersUpdates();

        void unsubscribeFromUsersUpdates();
    }
}
