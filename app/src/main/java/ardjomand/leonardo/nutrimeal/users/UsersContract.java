package ardjomand.leonardo.nutrimeal.users;

import ardjomand.leonardo.nutrimeal.auth.User;

interface UsersContract {

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
