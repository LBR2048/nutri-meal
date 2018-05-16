package ardjomand.leonardo.nutrimeal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import ardjomand.leonardo.nutrimeal.auth.AuthActivity;
import ardjomand.leonardo.nutrimeal.cart.CartFragment;
import ardjomand.leonardo.nutrimeal.cart.CartMeal;
import ardjomand.leonardo.nutrimeal.editmeal.EditMealFragment;
import ardjomand.leonardo.nutrimeal.meals.Meal;
import ardjomand.leonardo.nutrimeal.meals.MealsFragment;
import ardjomand.leonardo.nutrimeal.orders.OrdersFragment;

public class MainActivity extends AppCompatActivity implements
        MealsFragment.OnMealFragmentInteractionListener,
        CartFragment.OnOrderedMealFragmentInteractionListener {

    public static final String EDIT_MEAL_FRAGMENT_TAG = "edit-meal-fragment-tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(EDIT_MEAL_FRAGMENT_TAG);
        if (fragment != null && fragment.isVisible()) {
            EditMealFragment editMealFragment = (EditMealFragment) fragment;
            editMealFragment.updateMeal();
        }
        super.onBackPressed();
    }

    @Override
    public void onEditMealClicked(Meal meal) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, EditMealFragment.newInstance(meal.getKey(), "b"), EDIT_MEAL_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
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
    public void onOrderedMealClicked(CartMeal cartMeal) {
        Toast.makeText(this, "Edit " + cartMeal.getName() + " quantity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlaceOrderClicked() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, OrdersFragment.newInstance(1))
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
