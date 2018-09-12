package ardjomand.leonardo.nutrimeal.data;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ardjomand.leonardo.nutrimeal.customerorders.CustomerOrder;
import ardjomand.leonardo.nutrimeal.customerorders.CustomerOrdersPresenter;

public class CustomerOrdersRepositoryImpl implements GenericRepository.Repository {

    private static final String TAG = CustomerOrdersRepositoryImpl.class.getSimpleName();
    private static final String NODE_CUSTOMER_ORDERS = "customer-orders";

    private final CustomerOrdersPresenter presenter;
    private DatabaseReference ordersRef;
    private ChildEventListener ordersEventListener;

    public CustomerOrdersRepositoryImpl(CustomerOrdersPresenter presenter) {
        this.presenter = presenter;

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            ordersRef = FirebaseDatabase.getInstance().getReference()
                    .child(NODE_CUSTOMER_ORDERS)
                    .child(firebaseUser.getUid());
        }
    }

    @Override
    public void subscribe() {

        if (ordersEventListener == null) {
            ordersEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    CustomerOrder customerOrder = dataSnapshot.getValue(CustomerOrder.class);
                    if (customerOrder != null) {
                        customerOrder.setKey(dataSnapshot.getKey());
                        presenter.onItemAdded(customerOrder);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    CustomerOrder customerOrder = dataSnapshot.getValue(CustomerOrder.class);
                    if (customerOrder != null) {
                        customerOrder.setKey(dataSnapshot.getKey());
                        presenter.onItemChanged(customerOrder);
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    presenter.onItemRemoved(dataSnapshot.getKey());
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            ordersRef.addChildEventListener(ordersEventListener);
            Log.i(TAG, "Subscribing to order updates");
        }
    }

    @Override
    public void unsubscribe() {
        if (ordersEventListener != null) {
            ordersRef.removeEventListener(ordersEventListener);
            ordersEventListener = null;
            Log.i(TAG, "Unsubscribing from orders updates");
        }
    }
}
