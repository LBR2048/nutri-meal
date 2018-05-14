package ardjomand.leonardo.nutrimeal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ardjomand.leonardo.nutrimeal.cart.CartFragment;
import ardjomand.leonardo.nutrimeal.cart.CartMeal;
import ardjomand.leonardo.nutrimeal.editmeal.EditMealFragment;
import ardjomand.leonardo.nutrimeal.meals.Meal;
import ardjomand.leonardo.nutrimeal.meals.MealsFragment;
import ardjomand.leonardo.nutrimeal.orders.OrdersFragment;

public class MainActivity extends AppCompatActivity implements
        MealsFragment.OnMealFragmentInteractionListener,
        CartFragment.OnOrderedMealFragmentInteractionListener {

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
    public void onEditMealClicked(Meal meal) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, EditMealFragment.newInstance(meal.getKey(), "b"))
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
}
