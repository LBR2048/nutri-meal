package ardjomand.leonardo.nutrimeal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ardjomand.leonardo.nutrimeal.meals.Meal;
import ardjomand.leonardo.nutrimeal.meals.MealFragment;

public class MainActivity extends AppCompatActivity implements MealFragment.OnMealFragmentInteractionListener {

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
}
