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

import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrder;

public class CompanyWidgetRepositoryImpl implements CompanyWidgetRepository {

    private static final String NODE_ORDERS = "orders";

    public CompanyWidgetRepositoryImpl() {

    }

    @Override
    public void loadCompanyOrders(final LoadCompanyOrdersCallback loadCompanyOrdersCallback) {
        DatabaseReference ordersRef = getOrdersRef();

        if (ordersRef != null) {
            ordersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<CompanyOrder> companyOrders = new ArrayList<>();
                    for (DataSnapshot jobSnapshot: dataSnapshot.getChildren()) {
                        CompanyOrder companyOrder = jobSnapshot.getValue(CompanyOrder.class);
                        if (companyOrder != null) {
                            companyOrder.setKey(jobSnapshot.getKey());
                            companyOrders.add(companyOrder);
                        }
                    }
                    loadCompanyOrdersCallback.onComplete(companyOrders);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            loadCompanyOrdersCallback.onUserLoggedOut();
        }
    }

    private DatabaseReference getOrdersRef() {
        DatabaseReference ordersRef = null;
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            ordersRef = FirebaseDatabase.getInstance().getReference()
                    .child(NODE_ORDERS);
        }
        return ordersRef;
    }
}
