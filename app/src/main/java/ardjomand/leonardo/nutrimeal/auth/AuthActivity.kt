package ardjomand.leonardo.nutrimeal.auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import ardjomand.leonardo.nutrimeal.MainActivity
import ardjomand.leonardo.nutrimeal.R
import ardjomand.leonardo.nutrimeal.auth.account.AccountFragment
import ardjomand.leonardo.nutrimeal.auth.login.LoginFragment

class AuthActivity : AppCompatActivity(),
        LoginFragment.OnFragmentInteractionListener,
        AccountFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.auth_container_frame, LoginFragment.newInstance())
                    .commit()
        }
    }

    override fun onLoginSuccess() {
        navigateToMainActivity()
    }

    override fun onRegistrationSuccess() {
        navigateToMainActivity()
    }

    override fun onCreateAccountClicked() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.auth_container_frame, AccountFragment.newInstance())
                .addToBackStack(null)
                .commit()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
