package ardjomand.leonardo.nutrimeal.meals;

import android.util.Log;

public class MealPresenter implements MealContract.Presenter {

    public static final String TAG = MealPresenter.class.getSimpleName();

    private MealContract.View view;

    public MealPresenter(MealContract.View view) {
        this.view = view;
    }

    @Override
    public void getMeals() {
        Log.i(TAG, "Getting meals");
        view.showMeals(DummyMeals.ITEMS);
    }

    @Override
    public void addMealToCart(Meal meal) {
        Log.i(TAG, "Adding " + meal.getName() + " to cart");
    }
}
