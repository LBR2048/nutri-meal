package ardjomand.leonardo.nutrimeal.auth.account;

public interface AccountContract {

    interface Presenter {

        void createAccount(String name, String email, String password, String repeatedPassword);

        void getCurrentUser();
    }

    interface View {

        void goToMainScreen();

        void showError(String message);
    }
}
