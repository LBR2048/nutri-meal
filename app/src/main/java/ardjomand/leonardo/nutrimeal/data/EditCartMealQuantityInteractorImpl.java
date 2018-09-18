package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.database.DatabaseReference;

import ardjomand.leonardo.nutrimeal.data.common.FirebaseHelper;
import ardjomand.leonardo.nutrimeal.data.common.GenericItemRepository;
import ardjomand.leonardo.nutrimeal.data.pojos.CartMeal;

public class EditCartMealQuantityInteractorImpl implements
        EditCartMealQuantityInteractor {

    private final GenericItemRepository.Presenter<CartMeal> presenter;
    private DatabaseReference customerCartMealsRef;

    public EditCartMealQuantityInteractorImpl(GenericItemRepository.Presenter<CartMeal> presenter) {
        this.presenter = presenter;
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        customerCartMealsRef = firebaseHelper.getCustomerCartMealsRef();
    }

    @Override
    public void editCartMeal(CartMeal cartMeal) {
        customerCartMealsRef.child(cartMeal.getKey()).setValue(cartMeal);
    }
}
