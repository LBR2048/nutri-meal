package ardjomand.leonardo.nutrimeal.auth.account;

import ardjomand.leonardo.nutrimeal.auth.User;
import ardjomand.leonardo.nutrimeal.data.AuthRepository;
import ardjomand.leonardo.nutrimeal.data.AuthRepositoryImpl;

public class AccountPresenter implements AccountContract.Presenter {

    private final AuthRepository.Interactor authInteractor;
    private AccountContract.View view;

    public AccountPresenter(AccountContract.View view) {
        this.view = view;
        authInteractor = new AuthRepositoryImpl();
    }

    @Override
    public void setView(AccountContract.View view) {
        this.view = view;
    }

    @Override
    public void createAccount(String name, String email, String password, String repeatedPassword, String type) {
        // TODO check if any of the fields are null
        // TODO check if passwords match
        if (view != null) {
            view.showProgressBar(true);
        }

        User user = new User(name, email, type);
        authInteractor.createAccount(new AuthRepository.Interactor.CreateAccountCallback() {
            @Override
            public void onSuccess() {
                if (view != null){
                    view.showProgressBar(false);
                    view.goToMainScreen();
                }
            }

            @Override
            public void onFailure(Exception e) {
                if (view != null) {
                    view.showProgressBar(false);
                    view.showError(e.getMessage());
                }
            }
        }, user, password, type);
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
                }
            }

            @Override
            public void onFailure(Exception e) {
                if (view != null) {
                    view.showProgressBar(false);
                    view.showError(e.getMessage());
                }
            }
        });
    }
}
