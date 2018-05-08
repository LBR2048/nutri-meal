package ardjomand.leonardo.nutrimeal.meals;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.MealRepository;
import ardjomand.leonardo.nutrimeal.data.MealRepositoryImpl;

public class MealsPresenter implements MealsContract.Presenter, MealRepository.Presenter {

    public static final String TAG = MealsPresenter.class.getSimpleName();

    private MealsContract.View view;
    private final MealRepository.Repository repository;

    public MealsPresenter(MealsContract.View view) {
        this.view = view;
        repository = new MealRepositoryImpl(this);
    }

    @Override
    public void subscribeToMealsUpdates() {
        repository.subscribeForMealUpdates();
//        view.showMeals(DummyMeals.ITEMS);
    }

    @Override
    public void unsubscribeFromMealsUpdates() {
        repository.unsubscribeFromMealUpdates();
    }

    @Override
    public void addMealToCart(Meal meal) {
        Log.i(TAG, "Adding " + meal.getName() + " to cart");
    }

    @Override
    public void onMealAdded(Meal meal) {
        Log.i(TAG, "Meal " + meal.getName() + " added");
        view.addMeal(meal);
    }

    @Override
    public void onMealChanged(Meal meal) {
        Log.i(TAG, "Meal " + meal.getName() + " changed");
    }

    @Override
    public void onMealRemoved(String key) {
        Log.i(TAG, "Meal removed");
    }
}
