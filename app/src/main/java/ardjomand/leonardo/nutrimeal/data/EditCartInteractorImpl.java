package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ardjomand.leonardo.nutrimeal.cart.CartMeal;
import ardjomand.leonardo.nutrimeal.meals.Meal;
import ardjomand.leonardo.nutrimeal.meals.MealsPresenter;

public class EditCartInteractorImpl implements EditCartInteractor {

    private final DatabaseReference customerCartMealsRef;
    private final DatabaseReference customerCartAmountRef;

    public EditCartInteractorImpl(MealsPresenter presenter) {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        customerCartMealsRef = firebaseHelper.getCustomerCartMealsRef();
        customerCartAmountRef = firebaseHelper.getCustomerCartAmountRef();
    }

    @Override
    public void addMealToCart(final Meal meal) {

        // Check if meal already exists in cart
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // If yes, increment quantity and total cart amount
                    CartMeal cartMeal = dataSnapshot.getValue(CartMeal.class);
                    if (cartMeal != null) {
                        cartMeal.setQuantity(cartMeal.getQuantity() + 1);
                    }
                    customerCartMealsRef.child(meal.getKey()).setValue(cartMeal);
                    increaseAmount(meal.getUnitPrice());

                } else {
                    // If no, copy meal from menu to cart, set quantity to 1 and set total amount
                    CartMeal cartMeal = new CartMeal(meal.getKey(), meal.getName(), meal.getDescription(),
                            meal.getImagePath(), meal.getUnitPrice(), 1);
                    customerCartMealsRef.child(meal.getKey()).setValue(cartMeal);
                    increaseAmount(meal.getUnitPrice());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        customerCartMealsRef.child(meal.getKey()).addListenerForSingleValueEvent(valueEventListener);
    }

    private void increaseAmount(final long amountToIncrease) {
        ValueEventListener amountValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // If yes, increment quantity and total cart amount
                    Long amount = dataSnapshot.getValue(Long.class);
                    customerCartAmountRef.setValue(amount + amountToIncrease);

                } else {
                    // If no, copy meal from menu to cart, set quantity to 1 and set total amount
                    customerCartAmountRef.setValue(amountToIncrease);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        customerCartAmountRef.addListenerForSingleValueEvent(amountValueEventListener);
    }
}
