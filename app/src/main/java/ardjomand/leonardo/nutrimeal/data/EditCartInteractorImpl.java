package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ardjomand.leonardo.nutrimeal.cart.CartMeal;
import ardjomand.leonardo.nutrimeal.meals.Meal;
import ardjomand.leonardo.nutrimeal.meals.MealsPresenter;

public class EditCartInteractorImpl implements EditCartInteractor.Interactor {

    public static final String NODE_CUSTOMER_ORDERS = "customer-orders";
    public static final String NODE_MEALS = "meals";
    public static final String NODE_AMOUNT = "amount";
    public static final String NODE_CUSTOMER_CART = "customer-cart";

    private final MealsPresenter mealsPresenter;
    private final DatabaseReference mealsRef;
    private DatabaseReference customerCartRef;

    public EditCartInteractorImpl(MealsPresenter presenter) {
        mealsPresenter = presenter;

        mealsRef = FirebaseDatabase.getInstance().getReference()
                .child(NODE_MEALS);


        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            customerCartRef = FirebaseDatabase.getInstance().getReference()
                    .child(NODE_CUSTOMER_CART)
                    .child(firebaseUser.getUid());
        }
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
                    cartMeal.setQuantity(cartMeal.getQuantity() + 1);
                    customerCartRef.child(NODE_MEALS).child(meal.getKey()).setValue(cartMeal);
                    increaseAmount(meal.getUnitPrice());

                } else {
                    // If no, copy meal from menu to cart, set quantity to 1 and set total amount
                    CartMeal cartMeal = new CartMeal(meal.getKey(), meal.getName(), meal.getDescription(),
                            meal.getImagePath(), meal.getUnitPrice(), 1);
                    customerCartRef.child(NODE_MEALS).child(meal.getKey()).setValue(cartMeal);
                    increaseAmount(meal.getUnitPrice());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        customerCartRef.child(NODE_MEALS).child(meal.getKey()).addListenerForSingleValueEvent(valueEventListener);
    }

    private void increaseAmount(final long amountToIncrease) {
        ValueEventListener amountValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // If yes, increment quantity and total cart amount
                    Long amount = dataSnapshot.getValue(Long.class);
                    customerCartRef.child(NODE_AMOUNT).setValue(amount + amountToIncrease);

                } else {
                    // If no, copy meal from menu to cart, set quantity to 1 and set total amount
                    customerCartRef.child(NODE_AMOUNT).setValue(amountToIncrease);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        customerCartRef.child(NODE_AMOUNT).addListenerForSingleValueEvent(amountValueEventListener);
    }
}
