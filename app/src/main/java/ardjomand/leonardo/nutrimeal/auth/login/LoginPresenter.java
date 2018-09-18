package ardjomand.leonardo.nutrimeal.auth.login;

import ardjomand.leonardo.nutrimeal.data.AuthRepository;
import ardjomand.leonardo.nutrimeal.data.AuthRepositoryImpl;
import ardjomand.leonardo.nutrimeal.data.pojos.User;

public class LoginPresenter implements LoginContract.Presenter {

    private final AuthRepository.Interactor authInteractor;
    private LoginContract.View view;

    LoginPresenter(LoginContract.View view) {
        this.view = view;
        authInteractor = new AuthRepositoryImpl();
    }

    @Override
    public void setView(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void logIn(String email, String password) {
        if (view != null) {
            view.showProgressBar(true);
        }

        authInteractor.logIn(new AuthRepository.Interactor.LogInCallback() {
            @Override
            public void onSuccess() {
                if (view != null) {
                    view.showProgressBar(false);
                    view.goToMainScreen();
                }
            }

            @Override
            public void onFailure(Exception e) {
                if (view != null){
                    view.showProgressBar(false);
                    view.showError(e.getMessage());
                }
            }
        }, email, password);
    }

    @Override
    public void getCurrentUser() {
        if (view != null) {
            view.showProgressBar(true);
        }

        authInteractor.getCurrentUser(new AuthRepository.Interactor.GetCurrentUserCallback() {
            @Override
            public void onUserLoggedIn(User user) {
                if (view != null) {
                    view.showProgressBar(false);
                    view.goToMainScreen();
                }
            }

            @Override
            public void onUserLoggedOut() {
                if (view != null) {
                    view.showProgressBar(false);
                    view.showLoginForm(true);
                }
            }

            @Override
            public void onFailure(Exception e) {
                if (view != null) {
                    view.showProgressBar(false);
                    view.showLoginForm(true);
                    view.showError(e.getMessage());
                }
            }
        });
    }
}
