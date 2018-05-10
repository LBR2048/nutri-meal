package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ardjomand.leonardo.nutrimeal.cart.CartPresenter;

public class PlaceOrderInteractorImpl implements PlaceOrderInteractor.Interactor {

    public static final String NODE_CUSTOMER_ORDERS = "customer-orders";
    public static final String NODE_MEALS = "meals";
    public static final String NODE_CUSTOMER_CART = "customer-cart";

    // TODO add current customer ID
    private final String customerId = "customer1";

    private final CartPresenter cartPresenter;
    private final DatabaseReference customerOrdersRef;
    private final DatabaseReference customerCartRef;

    public PlaceOrderInteractorImpl(CartPresenter presenter) {
        cartPresenter = presenter;

        customerOrdersRef = FirebaseDatabase.getInstance().getReference()
                .child(NODE_CUSTOMER_ORDERS)
                .child(customerId);

        customerCartRef = FirebaseDatabase.getInstance().getReference()
                .child(NODE_CUSTOMER_CART)
                .child(customerId);
    }

    @Override
    public void placeOrder() {

        // TODO check if cart is not empty before placing order

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();

                // Push order to Firebase
                DatabaseReference orderRef = customerOrdersRef.push();
                orderRef.setValue(value);

                // Fill in missing properties
                orderRef.child("deliveryDate").setValue(System.currentTimeMillis());
                orderRef.child("delivered").setValue(false);

                // TODO delete cart contents
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        customerCartRef.addListenerForSingleValueEvent(valueEventListener);
    }

}
