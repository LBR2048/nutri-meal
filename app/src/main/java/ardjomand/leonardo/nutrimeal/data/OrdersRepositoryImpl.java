package ardjomand.leonardo.nutrimeal.data;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ardjomand.leonardo.nutrimeal.orders.Order;
import ardjomand.leonardo.nutrimeal.orders.OrdersPresenter;

public class OrdersRepositoryImpl implements OrdersRepository.Repository {

    public static final String TAG = OrdersRepositoryImpl.class.getSimpleName();
    public static final String NODE_CUSTOMER_ORDERS = "customer-orders";
    public static final String NODE_MEALS = "meals";

    // TODO add current customer ID
    private final String customerId = "customer1";

    // TODO add current order ID
    private final String orderId = "order1";

    private OrdersPresenter presenter;
    private DatabaseReference ordersRef;
    private ChildEventListener ordersEventListener;

    public OrdersRepositoryImpl(OrdersPresenter presenter) {
        this.presenter = presenter;

        ordersRef = FirebaseDatabase.getInstance().getReference()
                .child(NODE_CUSTOMER_ORDERS)
                .child(customerId);
    }

    @Override
    public void subscribeForOrdersUpdates() {

        if (ordersEventListener == null) {
            ordersEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    presenter.onOrderAdded(dataSnapshot.getValue(Order.class));
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    presenter.onOrderChanged(dataSnapshot.getValue(Order.class));
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    presenter.onOrderRemoved(dataSnapshot.getKey());
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
    public void unsubscribeFromOrdersUpdates() {
        if (ordersEventListener != null) {
            ordersRef.removeEventListener(ordersEventListener);
            ordersEventListener = null;
            Log.i(TAG, "Unsubscribing from orders updates");
        }
    }
}
