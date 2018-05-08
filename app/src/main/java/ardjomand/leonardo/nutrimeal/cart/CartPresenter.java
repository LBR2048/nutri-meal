package ardjomand.leonardo.nutrimeal.cart;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.CartRepository;
import ardjomand.leonardo.nutrimeal.data.CartRepositoryImpl;
import ardjomand.leonardo.nutrimeal.data.MealRepository;
import ardjomand.leonardo.nutrimeal.meals.Meal;

public class CartPresenter implements CartContract.Presenter, CartRepository.Presenter {

    public static final String TAG = CartPresenter.class.getSimpleName();

    private CartContract.View view;
    private final CartRepository.Repository repository;

    public CartPresenter(CartContract.View view) {
        this.view = view;
        repository = new CartRepositoryImpl(this);
    }

    @Override
    public void addMealToCart(Meal meal) {
        Log.i(TAG, "Adding " + meal.getName() + " to cart");
    }

    @Override
    public void getSelectedMeals() {
        Log.i(TAG, "Getting cart meals");
        repository.subscribeForCartUpdates();
    }

    @Override
    public void onSelectedMealAdded(SelectedMeal selectedMeal) {
        Log.i(TAG, "Selected meal " + selectedMeal.getName() + " added");
        view.addSelectedMeal(selectedMeal);
    }

    @Override
    public void onSelectedMealChanged(SelectedMeal selectedMeal) {

    }

    @Override
    public void onSelectedMealRemoved(String key) {

    }
}
