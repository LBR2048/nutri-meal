package ardjomand.leonardo.nutrimeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import ardjomand.leonardo.nutrimeal.auth.AuthActivity;
import ardjomand.leonardo.nutrimeal.cart.CartFragment;
import ardjomand.leonardo.nutrimeal.cart.CartMeal;
import ardjomand.leonardo.nutrimeal.cart.EditCartMealDialogFragment;
import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrdersFragment;
import ardjomand.leonardo.nutrimeal.customerorders.CustomerOrdersFragment;
import ardjomand.leonardo.nutrimeal.editmeal.EditMealFragment;
import ardjomand.leonardo.nutrimeal.meals.MealsFragment;

public class MainActivity extends AppCompatActivity implements
        MealsFragment.OnMealFragmentInteractionListener,
        CartFragment.OnOrderedMealFragmentInteractionListener {

    private static final String EDIT_MEAL_FRAGMENT_TAG = "edit-meal-fragment-tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, MealsFragment.newInstance(1))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_logout:
                logout();
                navigateToAuthActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEditMealClicked(String key) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, EditMealFragment.newInstance(key), EDIT_MEAL_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEditCartMealQuantity(String key) {
        EditCartMealDialogFragment.showDialog(this, key, "tag1");
    }

    @Override
    public void onGoToCartClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, CartFragment.newInstance(1))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onGoToOrdersClicked() {
        if (BuildConfig.FLAVOR.equals("company")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, CompanyOrdersFragment.newInstance(1))
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, CustomerOrdersFragment.newInstance(1))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onOrderedMealClicked(CartMeal cartMeal) {
        EditCartMealDialogFragment.showDialog(this, cartMeal.getKey(), "tag2");
    }

    @Override
    public void onPlaceOrderClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, CustomerOrdersFragment.newInstance(1))
                .addToBackStack(null)
                .commit();
    }

    private void logout() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
    }

    private void navigateToAuthActivity() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

}
