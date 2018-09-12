package ardjomand.leonardo.nutrimeal.users;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.auth.User;
import ardjomand.leonardo.nutrimeal.data.GenericRepository;
import ardjomand.leonardo.nutrimeal.data.GenericRepositoryImpl;

public class UsersPresenter implements
        UsersContract.Presenter,
        GenericRepository.Presenter<User> {

    private static final String TAG = UsersPresenter.class.getSimpleName();
    private final GenericRepository.Repository repository;
    private UsersContract.View view;

    UsersPresenter(UsersContract.View view) {
        this.view = view;
        repository = new GenericRepositoryImpl<>(this, User.class);
    }

    @Override
    public void setView(UsersContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribeToUsersUpdates() {
        repository.subscribe();
    }

    @Override
    public void unsubscribeFromUsersUpdates() {
        repository.unsubscribe();
    }

    @Override
    public void onItemAdded(User user) {
        Log.i(TAG, "User " + user.toString() + " added");
        if (view != null){
            view.addUser(user);
        }
    }

    @Override
    public void onItemChanged(User user) {
        if (view != null){
            view.updateUser(user);
        }
    }

    @Override
    public void onItemRemoved(String key) {

    }
}
