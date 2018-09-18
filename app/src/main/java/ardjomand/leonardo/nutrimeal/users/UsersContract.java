package ardjomand.leonardo.nutrimeal.users;

import ardjomand.leonardo.nutrimeal.data.pojos.User;

interface UsersContract {

    interface View {

        void addUser(User user);

        void updateUser(User user);

        void showEmptyUser();

        void showError();
    }

    interface Presenter {

        void setView(View view);

        void subscribeToUsersUpdates();

        void unsubscribeFromUsersUpdates();
    }
}
