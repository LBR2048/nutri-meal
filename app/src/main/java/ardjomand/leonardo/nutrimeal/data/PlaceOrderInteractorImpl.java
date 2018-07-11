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

    private static final String NODE_CUSTOMER_ORDERS = "customer-orders";
    private static final String NODE_ORDERS = "orders";
    private static final String NODE_CUSTOMER_CART = "customer-cart";

    private DatabaseReference customerOrdersRef;
    private DatabaseReference ordersRef;
    private DatabaseReference customerCartRef;

    public PlaceOrderInteractorImpl(CartPresenter presenter) {

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

                saveOrderInOrdersAndInCustomerOrders(value);

                // Delete cart contents
                customerCartRef.removeValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        customerCartRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void saveOrderInOrdersAndInCustomerOrders(Object value) {
        // Get a key that will be used for the order in both nodes
        String key = this.ordersRef.push().getKey();


        // Save order in orders/$key
        DatabaseReference ordersRef = this.ordersRef.child(key);
        ordersRef.setValue(value);

        // Fill in missing properties
        ordersRef.child("deliveryDate").setValue(System.currentTimeMillis());
        ordersRef.child("delivered").setValue(false);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            ordersRef.child("customerKey").setValue(firebaseUser.getUid());
        }


        // Save order in customer-orders/$key
        DatabaseReference customerOrdersRef = this.customerOrdersRef.child(key);
        customerOrdersRef.setValue(value);

        // Fill in missing properties
        customerOrdersRef.child("deliveryDate").setValue(System.currentTimeMillis());
        customerOrdersRef.child("delivered").setValue(false);
    }

}
