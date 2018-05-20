package ardjomand.leonardo.nutrimeal.users;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.auth.User;
import ardjomand.leonardo.nutrimeal.data.UsersRepository;
import ardjomand.leonardo.nutrimeal.data.UsersRepositoryImpl;

public class UsersPresenter implements UsersContract.Presenter, UsersRepository.Presenter {

    public static final String TAG = UsersPresenter.class.getSimpleName();
    private final UsersRepository.Repository repository;
    private UsersContract.View view;

    public UsersPresenter(UsersContract.View view) {
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