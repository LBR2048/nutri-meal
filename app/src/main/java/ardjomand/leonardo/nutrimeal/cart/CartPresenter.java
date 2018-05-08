package ardjomand.leonardo.nutrimeal.cart;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.CartRepository;
import ardjomand.leonardo.nutrimeal.data.CartRepositoryImpl;
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
    public void subscribeToCartUpdates() {
        Log.i(TAG, "Subscribing to cart updates");
        repository.subscribeForCartUpdates();
    }

    @Override
    public void unsubscribeFromCartUpdates() {
        Log.i(TAG, "Unsubscribing from cart updates");
        repository.unsubscribeFromCartUpdates();
    }

    @Override
    public void onSelectedMealAdded(CartMeal cartMeal) {
        Log.i(TAG, "Selected meal " + cartMeal.getName() + " added");
        view.addSelectedMeal(cartMeal);
    }

    @Override
    public void onSelectedMealChanged(CartMeal cartMeal) {

    }

    @Override
    public void onSelectedMealRemoved(String key) {

    }
}
