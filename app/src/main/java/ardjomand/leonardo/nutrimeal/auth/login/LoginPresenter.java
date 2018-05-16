package ardjomand.leonardo.nutrimeal.auth.login;

import ardjomand.leonardo.nutrimeal.auth.User;
import ardjomand.leonardo.nutrimeal.data.AuthRepository;
import ardjomand.leonardo.nutrimeal.data.AuthRepositoryImpl;

public class LoginPresenter implements LoginContract.Presenter {

    private final AuthRepository.Interactor authInteractor;
    private final LoginContract.View view;

    LoginPresenter(LoginContract.View view) {
        this.view = view;
        authInteractor = new AuthRepositoryImpl();
    }

    @Override
    public void logIn(String email, String password) {
        view.showProgressBar(true);

        authInteractor.logIn(new AuthRepository.Interactor.LogInCallback() {
            @Override
            public void onSuccess() {
                view.showProgressBar(false);
                view.goToMainScreen();
            }

            @Override
            public void onFailure(Exception e) {
                view.showProgressBar(false);
                view.showError(e.getMessage());
            }
        }, email, password);
    }

    @Override
    public void getCurrentUser() {
        view.showProgressBar(true);

        authInteractor.getCurrentUser(new AuthRepository.Interactor.GetCurrentUserCallback() {
            @Override
            public void onUserLoggedIn(User user) {
                view.showProgressBar(false);
                view.goToMainScreen();
            }

            @Override
            public void onUserLoggedOut() {
                view.showProgressBar(false);
                view.showLoginForm(true);
            }

            @Override
            public void onFailure(Exception e) {
                view.showProgressBar(false);
                view.showLoginForm(true);
                view.showError(e.getMessage());
            }
        });
    }
}
