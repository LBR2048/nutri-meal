package ardjomand.leonardo.nutrimeal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ardjomand.leonardo.nutrimeal.cart.CartFragment;
import ardjomand.leonardo.nutrimeal.cart.CartMeal;
import ardjomand.leonardo.nutrimeal.meals.Meal;
import ardjomand.leonardo.nutrimeal.meals.MealFragment;

public class MainActivity extends AppCompatActivity implements
        MealFragment.OnMealFragmentInteractionListener,
        CartFragment.OnOrderedMealFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, MealFragment.newInstance(1))
                    .commit();
        }
    }

    @Override
    public void onMealClicked(Meal item) {
        Toast.makeText(this, item.getName() + " clicked", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "Place order", Toast.LENGTH_SHORT).show();
    }
}
