package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ardjomand.leonardo.nutrimeal.cart.CartMeal;

public class EditCartMealQuantityInteractorImpl implements
        EditCartMealQuantityInteractor {

    private final GenericItemRepository.Presenter<CartMeal> presenter;
    private DatabaseReference customerCartMealsRef;

    public EditCartMealQuantityInteractorImpl(GenericItemRepository.Presenter<CartMeal> presenter) {
        this.presenter = presenter;

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            customerCartMealsRef = FirebaseDatabase.getInstance().getReference()
                    .child("customer-cart")
                    .child(firebaseUser.getUid())
                    .child("meals");
        }
    }

    @Override
    public void editCartMeal(CartMeal cartMeal) {
        customerCartMealsRef.child(cartMeal.getKey()).setValue(cartMeal);
    }
}
