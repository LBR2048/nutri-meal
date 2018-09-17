package ardjomand.leonardo.nutrimeal.cart;

import ardjomand.leonardo.nutrimeal.data.EditCartMealQuantityInteractor;
import ardjomand.leonardo.nutrimeal.data.EditCartMealQuantityInteractorImpl;
import ardjomand.leonardo.nutrimeal.data.GenericItemRepository;
import ardjomand.leonardo.nutrimeal.data.GenericItemRepositoryImpl;

public class EditCartMealDialogPresenter implements
        EditCartMealContract.Presenter,
        GenericItemRepository.Presenter<CartMeal> {

    private EditCartMealContract.View view;
    private final EditCartMealQuantityInteractor.Interactor editCartInteractor;
    private CartMeal cartMeal;
    private final GenericItemRepositoryImpl<CartMeal> genericItemRepository;

    EditCartMealDialogPresenter(EditCartMealContract.View view) {
        this.view = view;
        editCartInteractor = new EditCartMealQuantityInteractorImpl(this);
        genericItemRepository = new GenericItemRepositoryImpl<>(this, CartMeal.class);
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
