package ardjomand.leonardo.nutrimeal;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import ardjomand.leonardo.nutrimeal.widget.OrdersWidget;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // Update widgets if user logs in or out
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                OrdersWidget.updateAllWidgets(getApplicationContext());

                String uid = firebaseAuth.getUid();
                if (uid != null) {
                    Log.d("Auth", "User logged in: " + uid);
                } else {
                    Log.d("Auth", "User logged out");
                }
            }
        });
    }
}
