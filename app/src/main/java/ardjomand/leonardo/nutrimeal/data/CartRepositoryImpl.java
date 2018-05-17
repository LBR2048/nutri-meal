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
import ardjomand.leonardo.nutrimeal.cart.CartPresenter;

public class CartRepositoryImpl implements CartRepository.Repository {

    public static final String NODE_CUSTOMER_CART = "customer-cart";
    public static final String NODE_MEALS = "meals";
    public static final String TAG = CartRepositoryImpl.class.getSimpleName();

    private CartPresenter presenter;
    private DatabaseReference customerCartRef;
    private ChildEventListener cartEventListener;

    public CartRepositoryImpl(CartPresenter presenter) {
        this.presenter = presenter;

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            customerCartRef = FirebaseDatabase.getInstance().getReference()
                    .child(NODE_CUSTOMER_CART).child(firebaseUser.getUid()).child(NODE_MEALS);
        }
    }

    @Override
    public void subscribeForCartUpdates() {

        if (cartEventListener == null) {
            cartEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    presenter.onSelectedMealAdded(dataSnapshot.getValue(CartMeal.class));
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    presenter.onSelectedMealChanged(dataSnapshot.getValue(CartMeal.class));
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    presenter.onSelectedMealRemoved(dataSnapshot.getKey());
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            customerCartRef.addChildEventListener(cartEventListener);
            Log.i(TAG, "Subscribing to cart updates");
        }
    }

    @Override
    public void unsubscribeFromCartUpdates() {
        if (cartEventListener != null) {
            customerCartRef.removeEventListener(cartEventListener);
            cartEventListener = null;
            Log.i(TAG, "Unsubscribing from cart updates");
        }
    }
}
