package ardjomand.leonardo.nutrimeal.cart;

import ardjomand.leonardo.nutrimeal.data.EditCartMealQuantityInteractor;
import ardjomand.leonardo.nutrimeal.data.EditCartMealQuantityInteractorImpl;

public class EditCartMealDialogPresenter implements EditCartMealContract.Presenter, EditCartMealQuantityInteractor.Presenter {

    private final EditCartMealContract.View view;
    private final EditCartMealQuantityInteractor.Interactor editCartInteractor;

    public EditCartMealDialogPresenter(EditCartMealContract.View view) {
        this.view = view;
        editCartInteractor = new EditCartMealQuantityInteractorImpl(this);
    }

    @Override
    public void subscribeForCartMealsUpdates(String key) {
        editCartInteractor.subscribeForCartMealUpdates(key);
    }

    @Override
    public void unsubscribeFromCartMealsUpdates() {
        editCartInteractor.unsubscribeFromCartMealUpdates();
    }

    @Override
    public void onCartMealUpdated(CartMeal cartMeal) {
        view.updateCartMeal(cartMeal);
    }

    @Override
    public void editCartMeal(CartMeal cartMeal) {
        editCartInteractor.editCartMeal(cartMeal);
    }
}
