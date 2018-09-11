package ardjomand.leonardo.nutrimeal.data;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrder;
import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrdersPresenter;

public class CompanyOrdersRepositoryImpl implements GenericRepository.Repository {

    private static final String TAG = CompanyOrdersRepositoryImpl.class.getSimpleName();
    private static final String NODE_ORDERS = "orders";

    private final CompanyOrdersPresenter presenter;
    private DatabaseReference ordersRef;
    private ChildEventListener ordersEventListener;

    public CompanyOrdersRepositoryImpl(CompanyOrdersPresenter presenter) {
        this.presenter = presenter;

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            ordersRef = FirebaseDatabase.getInstance().getReference()
                    .child(NODE_ORDERS);
        }
    }

    @Override
    public void subscribe() {

        if (ordersEventListener == null) {
            ordersEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    CompanyOrder companyOrder = dataSnapshot.getValue(CompanyOrder.class);
                    if (companyOrder != null) {
                        companyOrder.setKey(dataSnapshot.getKey());
                        presenter.onItemAdded(companyOrder);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    CompanyOrder companyOrder = dataSnapshot.getValue(CompanyOrder.class);
                    if (companyOrder != null) {
                        companyOrder.setKey(dataSnapshot.getKey());
                        presenter.onItemChanged(companyOrder);
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
