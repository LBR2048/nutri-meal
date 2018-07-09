package ardjomand.leonardo.nutrimeal.auth.login;

public interface LoginContract {

    interface Presenter {

        void setView(View view);

        void logIn(String email, String password);

        void getCurrentUser();
    }

    interface View {

        void showProgressBar(boolean visibility);

        void showLoginForm(boolean visibility);

        void goToMainScreen();

        void showError(String message);
    }
}
