package ardjomand.leonardo.nutrimeal.cart;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.CartRepository;
import ardjomand.leonardo.nutrimeal.data.CartRepositoryImpl;
import ardjomand.leonardo.nutrimeal.data.PlaceOrderInteractor;
import ardjomand.leonardo.nutrimeal.data.PlaceOrderInteractorImpl;
import ardjomand.leonardo.nutrimeal.meals.Meal;

public class CartPresenter implements CartContract.Presenter, CartRepository.Presenter {

    public static final String TAG = CartPresenter.class.getSimpleName();

    private CartContract.View view;
    private final CartRepository.Repository repository;
    private final PlaceOrderInteractor.Interactor placeOrderInteractor;

    CartPresenter(CartContract.View view) {
        this.view = view;
        repository = new CartRepositoryImpl(this);
        placeOrderInteractor = new PlaceOrderInteractorImpl(this);
    }

    @Override
    public void setView(CartContract.View view) {
        this.view = view;
    }

    @Override
    public void addMealToCart(Meal meal) {
        Log.i(TAG, "Adding " + meal.getName() + " to cart");
    }

    @Override
    public void subscribeToCartUpdates() {
        repository.subscribeForCartUpdates();
    }

    @Override
    public void unsubscribeFromCartUpdates() {
        repository.unsubscribeFromCartUpdates();
    }

    @Override
    public void placeOrder() {
        placeOrderInteractor.placeOrder();
    }

    @Override
    public void onSelectedMealAdded(CartMeal cartMeal) {
        Log.i(TAG, "Selected meal " + cartMeal.getName() + " added");
        if (view != null) {
            view.addCartMeal(cartMeal);
        }
    }

    @Override
    public void onSelectedMealChanged(CartMeal cartMeal) {
        if (view != null){
            view.updateCartMeal(cartMeal);
        }
    }

    @Override
    public void onSelectedMealRemoved(String key) {

    }
}
