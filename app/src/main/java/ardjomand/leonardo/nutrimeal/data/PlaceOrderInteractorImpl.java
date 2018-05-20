package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ardjomand.leonardo.nutrimeal.cart.CartPresenter;

public class PlaceOrderInteractorImpl implements PlaceOrderInteractor.Interactor {

    public static final String NODE_CUSTOMER_ORDERS = "customer-orders";
    public static final String NODE_ORDERS = "orders";
    public static final String NODE_MEALS = "meals";
    public static final String NODE_CUSTOMER_CART = "customer-cart";

    private final CartPresenter cartPresenter;
    private DatabaseReference customerOrdersRef;
    private DatabaseReference ordersRef;
    private DatabaseReference customerCartRef;

    public PlaceOrderInteractorImpl(CartPresenter presenter) {
        cartPresenter = presenter;

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            customerOrdersRef = FirebaseDatabase.getInstance().getReference()
                    .child(NODE_CUSTOMER_ORDERS)
                    .child(firebaseUser.getUid());

            ordersRef = FirebaseDatabase.getInstance().getReference()
                    .child(NODE_ORDERS);

            customerCartRef = FirebaseDatabase.getInstance().getReference()
                    .child(NODE_CUSTOMER_CART)
                    .child(firebaseUser.getUid());
        }
    }

    @Override
    public void placeOrder() {

        // TODO check if cart is not empty before placing order

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();

                placeOrderInCustomerOrders(value);
                placeOrderInOrders(value);

                // Delete cart contents
                customerCartRef.removeValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        customerCartRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void placeOrderInCustomerOrders(Object value) {
        DatabaseReference orderRef = customerOrdersRef.push();
        orderRef.setValue(value);

        // Fill in missing properties
        orderRef.child("deliveryDate").setValue(System.currentTimeMillis());
        orderRef.child("delivered").setValue(false);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            orderRef.child("customerKey").setValue(firebaseUser.getUid());
        }
    }

    private void placeOrderInOrders(Object value) {
        DatabaseReference orderRef = ordersRef.push();
        orderRef.setValue(value);

        // Fill in missing properties
        orderRef.child("deliveryDate").setValue(System.currentTimeMillis());
        orderRef.child("delivered").setValue(false);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            orderRef.child("customerKey").setValue(firebaseUser.getUid());
        }
    }

}
