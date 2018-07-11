package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrder;

public class CompanyWidgetRepositoryImpl implements CompanyWidgetRepository {

    public static final String TAG = CompanyWidgetRepositoryImpl.class.getSimpleName();
    private static final String NODE_ORDERS = "orders";
    public static final String NODE_MEALS = "meals";

    // TODO add current order ID
    private final String orderId = "order1";

    private DatabaseReference ordersRef;
    private ChildEventListener ordersEventListener;

    public CompanyWidgetRepositoryImpl() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            ordersRef = FirebaseDatabase.getInstance().getReference()
                    .child(NODE_ORDERS);
        }
    }

    @Override
    public void loadCompanyOrders(final LoadCompanyOrdersCallback loadCompanyOrdersCallback) {
        ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<CompanyOrder> companyOrders = new ArrayList<>();
                for (DataSnapshot jobSnapshot: dataSnapshot.getChildren()) {
                    CompanyOrder companyOrder = jobSnapshot.getValue(CompanyOrder.class);
                    companyOrder.setKey(jobSnapshot.getKey());
                    companyOrders.add(companyOrder);
                }
                loadCompanyOrdersCallback.onComplete(companyOrders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
