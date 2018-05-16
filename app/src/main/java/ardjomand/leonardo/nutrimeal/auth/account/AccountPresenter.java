package ardjomand.leonardo.nutrimeal.auth.account;

import ardjomand.leonardo.nutrimeal.auth.User;
import ardjomand.leonardo.nutrimeal.data.AuthRepository;
import ardjomand.leonardo.nutrimeal.data.AuthRepositoryImpl;

public class AccountPresenter implements AccountContract.Presenter {

    private final AuthRepository.Interactor authInteractor;
    private final AccountContract.View view;

    public AccountPresenter(AccountContract.View view) {
        this.view = view;
        authInteractor = new AuthRepositoryImpl();
    }

    @Override
    public void createAccount(String name, String email, String password, String repeatedPassword) {
        // TODO check if passwords match
        User user = new User(name, email);
        authInteractor.createAccount(new AuthRepository.Interactor.CreateAccountCallback() {
            @Override
            public void onSuccess() {
                view.goToMainScreen();
            }

            @Override
            public void onFailure(Exception e) {
                view.showError(e.getMessage());
            }
        }, password, user);
    }

    @Override
    public void getCurrentUser() {
        authInteractor.getCurrentUser(new AuthRepository.Interactor.GetCurrentUserCallback() {
            @Override
            public void onUserLoggedIn(User user) {
                view.goToMainScreen();
            }

            @Override
            public void onUserLoggedOut() {
                // Do nothing
            }

            @Override
            public void onFailure(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }
}
