package ardjomand.leonardo.nutrimeal.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ardjomand.leonardo.nutrimeal.auth.User;

public class AuthRepositoryImpl implements AuthRepository.Interactor {

    private static final String TAG = AuthRepositoryImpl.class.getSimpleName();
    private static final String NODE_USERS = "users";

    private final FirebaseAuth auth;
    private final DatabaseReference usersRef;

    public AuthRepositoryImpl() {
        auth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child(NODE_USERS);
    }

    @Override
    public void createAccount(final CreateAccountCallback createAccountCallback, final User user, String password, String type) {
        auth.createUserWithEmailAndPassword(user.getEmail(), password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            if (firebaseUser != null) {
                                usersRef.child(firebaseUser.getUid()).setValue(user);
                            }
                            createAccountCallback.onSuccess();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            createAccountCallback.onFailure(task.getException());
//                            updateUI(null);
                        }
                    }
                });
    }

    @Override
    public void logIn(final LogInCallback logInCallback, String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            logInCallback.onSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            logInCallback.onFailure(task.getException());
                        }
                    }
                });
    }

    @Override
    public void getCurrentUser(final GetCurrentUserCallback getCurrentUserCallback) {
        FirebaseUser firebaseUser = auth.getCurrentUser();

        if (firebaseUser != null) {
            usersRef.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    getCurrentUserCallback.onUserLoggedIn(user);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    getCurrentUserCallback.onFailure(databaseError.toException());
                }
            });
        } else {
            getCurrentUserCallback.onUserLoggedOut();
        }
    }
}
