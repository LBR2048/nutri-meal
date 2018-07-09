package ardjomand.leonardo.nutrimeal.auth.account;

public interface AccountContract {

    interface Presenter {

        void setView(View view);

        void createAccount(String name, String email, String password, String repeatedPassword, String type);

        void getCurrentUser();
    }

    interface View {

        void showProgressBar(boolean visibility);

        void goToMainScreen();

        void showError(String message);
    }
}
