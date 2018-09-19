package ardjomand.leonardo.nutrimeal

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import ardjomand.leonardo.nutrimeal.auth.AuthActivity
import ardjomand.leonardo.nutrimeal.cart.CartFragment
import ardjomand.leonardo.nutrimeal.cart.EditCartMealDialogFragment
import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrdersFragment
import ardjomand.leonardo.nutrimeal.customerorders.CustomerOrdersFragment
import ardjomand.leonardo.nutrimeal.data.pojos.CartMeal
import ardjomand.leonardo.nutrimeal.editmeal.EditMealFragment
import ardjomand.leonardo.nutrimeal.meals.MealsFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(),
        MealsFragment.OnMealFragmentInteractionListener,
        CartFragment.OnOrderedMealFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, MealsFragment.newInstance(1))
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> supportFragmentManager.popBackStack()

            R.id.menu_item_logout -> {
                logout()
                navigateToAuthActivity()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onEditMealClicked(key: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, EditMealFragment.newInstance(key), EDIT_MEAL_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit()
    }

    override fun onEditCartMealQuantity(key: String) {
        // TODO this is not following the MVP pattern
        if (Utils.isNetworkAvailable(this)) {
            EditCartMealDialogFragment.showDialog(this, key, "tag1")
        } else {
            Toast.makeText(this, R.string.error_internet_connection, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onGoToCartClicked() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, CartFragment.newInstance(1))
                .addToBackStack(null)
                .commit()
    }

    override fun onGoToOrdersClicked() {
        if (BuildConfig.FLAVOR == "company") {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, CompanyOrdersFragment.newInstance(1))
                    .addToBackStack(null)
                    .commit()
        } else {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, CustomerOrdersFragment.newInstance(1))
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onOrderedMealClicked(item: CartMeal) {
        EditCartMealDialogFragment.showDialog(this, item.key, "tag2")
    }

    override fun onPlaceOrderClicked() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, CustomerOrdersFragment.newInstance(1))
                .addToBackStack(null)
                .commit()
    }

    private fun logout() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
    }

    private fun navigateToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {

        private const val EDIT_MEAL_FRAGMENT_TAG = "edit-meal-fragment-tag"
    }

}
