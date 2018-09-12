package ardjomand.leonardo.nutrimeal.data;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ardjomand.leonardo.nutrimeal.cart.CartMeal;


public class CartRepositoryImpl<T extends CartMeal> implements GenericRepository.Repository {

    private static final String NODE_CUSTOMER_CART = "customer-cart";
    private static final String NODE_MEALS = "meals";
    private static final String TAG = CartRepositoryImpl.class.getSimpleName();

    private final GenericRepository.Presenter<T> presenter;
    private final Class<T> clazz;

    private DatabaseReference customerCartRef;
    private ChildEventListener cartEventListener;

    public CartRepositoryImpl(GenericRepository.Presenter<T> presenter, Class<T> clazz) {
        this.presenter = presenter;
        this.clazz = clazz;

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            customerCartRef = FirebaseDatabase.getInstance().getReference()
                    .child(NODE_CUSTOMER_CART).child(firebaseUser.getUid()).child(NODE_MEALS);
        }
    }

    @Override
    public void subscribe() {
        if (cartEventListener == null) {
            cartEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    T item = dataSnapshot.getValue(clazz);
                    if (item != null) {
                        item.setKey(dataSnapshot.getKey());
                        presenter.onItemAdded(item);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    T item = dataSnapshot.getValue(clazz);
                    if (item != null) {
                        item.setKey(dataSnapshot.getKey());
                        presenter.onItemChanged(item);
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
                    Log.e(TAG, databaseError.getMessage());
                }
            };
            customerCartRef.addChildEventListener(cartEventListener);
            Log.i(TAG, "Subscribing to cart updates");
        }
    }

    @Override
    public void unsubscribe() {
        if (cartEventListener != null) {
            customerCartRef.removeEventListener(cartEventListener);
            cartEventListener = null;
            Log.i(TAG, "Unsubscribing from cart updates");
        }
    }
}
