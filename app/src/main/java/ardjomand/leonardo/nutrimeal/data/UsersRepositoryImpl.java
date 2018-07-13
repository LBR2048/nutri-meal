package ardjomand.leonardo.nutrimeal.data;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ardjomand.leonardo.nutrimeal.auth.User;
import ardjomand.leonardo.nutrimeal.users.UsersPresenter;

public class UsersRepositoryImpl implements UsersRepository.Repository {

    private static final String TAG = UsersRepositoryImpl.class.getSimpleName();
    private static final String NODE_USERS = "users";

    private final UsersPresenter presenter;
    private final DatabaseReference usersRef;
    private ChildEventListener usersEventListener;

    public UsersRepositoryImpl(UsersPresenter presenter) {
        this.presenter = presenter;

        usersRef = FirebaseDatabase.getInstance().getReference().child(NODE_USERS);
    }

    @Override
    public void subscribeForUsersUpdates() {
        if (usersEventListener == null) {
            usersEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        user.setKey(dataSnapshot.getKey());
                        presenter.onUserAdded(user);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        user.setKey(dataSnapshot.getKey());
                        presenter.onUserChanged(user);
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    presenter.onUserRemoved(dataSnapshot.getKey());
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            usersRef.orderByChild("type").equalTo("customer").addChildEventListener(usersEventListener);
            Log.i(TAG, "Subscribing to users updates");
        }
    }

    @Override
    public void unsubscribeFromUsersUpdates() {
        if (usersEventListener != null) {
            usersRef.removeEventListener(usersEventListener);
            usersEventListener = null;
            Log.i(TAG, "Unsubscribing from users updates");
        }
    }
}
