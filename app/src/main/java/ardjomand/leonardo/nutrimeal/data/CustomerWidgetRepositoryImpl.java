package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ardjomand.leonardo.nutrimeal.customerorders.CustomerOrder;

public class CustomerWidgetRepositoryImpl implements CustomerWidgetRepository {

    private static final String NODE_CUSTOMER_ORDERS = "customer-orders";

    private DatabaseReference ordersRef;

    public CustomerWidgetRepositoryImpl() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            ordersRef = FirebaseDatabase.getInstance().getReference()
                    .child(NODE_CUSTOMER_ORDERS)
                    .child(firebaseUser.getUid());
        }
    }

    @Override
    public void loadCustomerOrders(final LoadCustomersOrdersCallback loadCustomersOrdersCallback) {
        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<CustomerOrder> customerOrders = new ArrayList<>();
                for (DataSnapshot jobSnapshot: dataSnapshot.getChildren()) {
                    CustomerOrder customerOrder = jobSnapshot.getValue(CustomerOrder.class);
                    if (customerOrder != null) {
                        customerOrder.setKey(jobSnapshot.getKey());
                        customerOrders.add(customerOrder);
                    }
                }
                loadCustomersOrdersCallback.onComplete(customerOrders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
