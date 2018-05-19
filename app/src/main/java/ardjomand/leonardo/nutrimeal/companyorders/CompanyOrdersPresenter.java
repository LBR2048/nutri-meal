package ardjomand.leonardo.nutrimeal.companyorders;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.auth.User;
import ardjomand.leonardo.nutrimeal.data.UsersRepository;
import ardjomand.leonardo.nutrimeal.data.UsersRepositoryImpl;

public class CompanyOrdersPresenter implements CompanyOrdersContract.Presenter, UsersRepository.Presenter {

    public static final String TAG = CompanyOrdersPresenter.class.getSimpleName();
    private final UsersRepository.Repository repository;
    private CompanyOrdersContract.View view;

    public CompanyOrdersPresenter(CompanyOrdersContract.View view) {
        this.view = view;
        repository = new UsersRepositoryImpl(this);
    }

    @Override
    public void subscribeToUsersUpdates() {
        repository.subscribeForUsersUpdates();
    }

    @Override
    public void unsubscribeFromUsersUpdates() {
        repository.unsubscribeFromUsersUpdates();
    }

    @Override
    public void onUserAdded(User user) {
        Log.i(TAG, "User " + user.toString() + " added");
        view.addUser(user);
    }

    @Override
    public void onUserChanged(User user) {
        view.updateUser(user);
    }

    @Override
    public void onUserRemoved(String key) {

    }
}
