package ardjomand.leonardo.nutrimeal.data;

import ardjomand.leonardo.nutrimeal.auth.User;

public interface AuthRepository {

    interface Interactor {

        interface CreateAccountCallback {

            void onSuccess();

            void onFailure(Exception e);
        }

        void createAccount(CreateAccountCallback createAccountCallback, User user,
                           String password, String type);


        interface LogInCallback {

            void onSuccess();

            void onFailure(Exception e);
        }

        void logIn(LogInCallback logInCallback, String email, String password);


        interface GetCurrentUserCallback {

            void onUserLoggedIn(User user);

            void onUserLoggedOut();

            void onFailure(Exception e);
        }

        void getCurrentUser(GetCurrentUserCallback getCurrentUserCallback);

    }

    interface Presenter {

    }
}
