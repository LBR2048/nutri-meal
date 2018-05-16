package ardjomand.leonardo.nutrimeal.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ardjomand.leonardo.nutrimeal.MainActivity;
import ardjomand.leonardo.nutrimeal.R;
import ardjomand.leonardo.nutrimeal.auth.account.AccountFragment;
import ardjomand.leonardo.nutrimeal.auth.login.LoginFragment;

public class AuthActivity extends AppCompatActivity implements
        LoginFragment.OnFragmentInteractionListener,
        AccountFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.auth_container_frame, LoginFragment.newInstance("a", "b"))
                    .commit();
        }
    }

    @Override
    public void onLoginSuccess() {
        navigateToMainActivity();
    }

    @Override
    public void onRegistrationSuccess() {
        navigateToMainActivity();
    }

    @Override
    public void onCreateAccountClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.auth_container_frame, AccountFragment.newInstance("a", "b"))
                .addToBackStack(null)
                .commit();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
