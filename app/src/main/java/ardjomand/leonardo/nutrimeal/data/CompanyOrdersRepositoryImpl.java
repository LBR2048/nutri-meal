package ardjomand.leonardo.nutrimeal.data;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class CompanyOrdersRepositoryImpl<T extends KeyClass> implements GenericRepository.Repository {

    private static final String TAG = CompanyOrdersRepositoryImpl.class.getSimpleName();

    private final GenericRepository.Presenter<T> presenter;
    private final Class<T> clazz;

    private DatabaseReference itemsRef;
    private ChildEventListener itemsEventListener;

    public CompanyOrdersRepositoryImpl(GenericRepository.Presenter<T> presenter, Class<T> clazz) {
        this.presenter = presenter;
        this.clazz = clazz;

        FirebaseHelper firebaseHelper = new FirebaseHelper();

        itemsRef = firebaseHelper.getOrdersRef();

//        itemsRef = firebaseHelper.getItemsRef(clazz);
    }

    @Override
    public void subscribe() {
        if (itemsEventListener == null) {
            itemsEventListener = new ChildEventListener() {
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

                }
            };
            itemsRef.addChildEventListener(itemsEventListener);
            Log.i(TAG, "Subscribing to order updates");
        }
    }

    @Override
    public void unsubscribe() {
        if (itemsEventListener != null) {
            itemsRef.removeEventListener(itemsEventListener);
            itemsEventListener = null;
            Log.i(TAG, "Unsubscribing from orders updates");
        }
    }
}
