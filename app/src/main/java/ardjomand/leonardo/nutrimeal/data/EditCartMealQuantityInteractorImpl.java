package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ardjomand.leonardo.nutrimeal.cart.CartMeal;

public class EditCartMealQuantityInteractorImpl implements EditCartMealQuantityInteractor.Interactor {

    private final EditCartMealQuantityInteractor.Presenter presenter;
    private ValueEventListener cartMealEventListener;
    private DatabaseReference customerCartMealsRef;

    public EditCartMealQuantityInteractorImpl(EditCartMealQuantityInteractor.Presenter presenter) {
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
    public void subscribeForCartMealUpdates(String key) {
        if (cartMealEventListener == null) {
            cartMealEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    CartMeal cartMeal = dataSnapshot.getValue(CartMeal.class);
                    if (cartMeal != null){
                        cartMeal.setKey(dataSnapshot.getKey());
                        presenter.onCartMealUpdated(cartMeal);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            customerCartMealsRef.child(key).addValueEventListener(cartMealEventListener);
        }
    }

    @Override
    public void unsubscribeFromCartMealUpdates() {
        if (cartMealEventListener != null) {
            customerCartMealsRef.removeEventListener(cartMealEventListener);
        }
    }

    @Override
    public void editCartMeal(CartMeal cartMeal) {
        customerCartMealsRef.child(cartMeal.getKey()).setValue(cartMeal);
    }
}
