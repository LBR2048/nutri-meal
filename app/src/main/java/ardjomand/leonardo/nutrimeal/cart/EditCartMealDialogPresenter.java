package ardjomand.leonardo.nutrimeal.cart;

import ardjomand.leonardo.nutrimeal.data.EditCartMealQuantityInteractor;
import ardjomand.leonardo.nutrimeal.data.EditCartMealQuantityInteractorImpl;
import ardjomand.leonardo.nutrimeal.data.common.GenericItemRepository;
import ardjomand.leonardo.nutrimeal.data.common.GenericItemRepositoryImpl;
import ardjomand.leonardo.nutrimeal.data.pojos.CartMeal;

public class EditCartMealDialogPresenter implements
        EditCartMealContract.Presenter,
        GenericItemRepository.Presenter<CartMeal> {

    private EditCartMealContract.View view;
    private final EditCartMealQuantityInteractor editCartInteractor;
    private CartMeal cartMeal;
    private final GenericItemRepositoryImpl<CartMeal> genericItemRepository;

    EditCartMealDialogPresenter(EditCartMealContract.View view, String key) {
        this.view = view;
        editCartInteractor = new EditCartMealQuantityInteractorImpl(this);
        genericItemRepository = new GenericItemRepositoryImpl<>(this, CartMeal.class, key);
    }

    @Override
    public void setView(EditCartMealContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe(String key) {
        genericItemRepository.subscribe(key);
    }

    @Override
    public void unsubscribe() {
        genericItemRepository.unsubscribe();
    }

    @Override
    public void onItemRead(CartMeal cartMeal) {
        if (view != null){
            this.cartMeal = cartMeal;
            view.updateCartMeal(cartMeal);
        }
    }

    @Override
    public void increaseQuantity() {
        if (cartMeal != null) {
            cartMeal.increaseQuantity();
            editCartInteractor.editCartMeal(cartMeal);
        }
    }

    @Override
    public void decreaseQuantity() {
        if (cartMeal != null) {
            cartMeal.decreaseQuantity();
            editCartInteractor.editCartMeal(cartMeal);
        }
    }
}
