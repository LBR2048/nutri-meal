package ardjomand.leonardo.nutrimeal.orders;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrdersRepositoryImpl implements OrdersRepository.Repository {

    public static final String NODE_CUSTOMER_ORDERS = "customer-orders";
    public static final String NODE_SELECTED_MEALS = "selected-meals";

    // TODO add current customer ID
    private final String customerId = "customer1";

    // TODO add current order ID
    private final String orderId = "order1";

    private OrdersPresenter presenter;
    private DatabaseReference ordersRef;
    private ChildEventListener ordersEventListener;

    public OrdersRepositoryImpl(OrdersPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void subscribeForOrdersUpdates() {

        ordersRef = FirebaseDatabase.getInstance().getReference()
                .child(NODE_CUSTOMER_ORDERS)
                .child(customerId)
                .child(orderId)
                .child(OrdersRepositoryImpl.NODE_SELECTED_MEALS);

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
    }

    @Override
    public void unsubscribeFromOrdersUpdates() {
        ordersRef.removeEventListener(ordersEventListener);
    }
}
