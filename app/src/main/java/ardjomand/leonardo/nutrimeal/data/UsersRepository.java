package ardjomand.leonardo.nutrimeal.data;

import ardjomand.leonardo.nutrimeal.auth.User;

public interface UsersRepository {

    interface Repository {

        void subscribeForUsersUpdates();

        void unsubscribeFromUsersUpdates();
    }

    interface Presenter {

        void onUserAdded(User user);

        void onUserChanged(User user);

        void onUserRemoved(String key);
    }
}
