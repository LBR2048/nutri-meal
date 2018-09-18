package ardjomand.leonardo.nutrimeal.data.common;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import ardjomand.leonardo.nutrimeal.data.pojos.KeyClass;

public class GenericItemRepositoryImpl<T extends KeyClass> implements GenericItemRepository.Repository {

    private static final String TAG = GenericItemRepositoryImpl.class.getSimpleName();

    private final GenericItemRepository.Presenter<T> presenter;
    private final Class<T> clazz;

    private DatabaseReference itemsRef;
    private ValueEventListener valueEventListener;

    public GenericItemRepositoryImpl(
            GenericItemRepository.Presenter<T> presenter,
            Class<T> clazz,
            String key) {
        this.presenter = presenter;
        this.clazz = clazz;

        FirebaseHelper firebaseHelper = new FirebaseHelper();

        itemsRef = firebaseHelper.getItemRef(clazz, key);
    }

    @Override
    public void subscribe(String key) {
        if (valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    T cartMeal = dataSnapshot.getValue(clazz);
                    if (cartMeal != null){
                        cartMeal.setKey(dataSnapshot.getKey());
                        presenter.onItemRead(cartMeal);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            itemsRef.addValueEventListener(valueEventListener);
        }
    }

    @Override
    public void unsubscribe() {
        if (valueEventListener != null) {
            itemsRef.removeEventListener(valueEventListener);
        }
    }
}
