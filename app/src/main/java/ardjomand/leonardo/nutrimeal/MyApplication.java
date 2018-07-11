package ardjomand.leonardo.nutrimeal;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
