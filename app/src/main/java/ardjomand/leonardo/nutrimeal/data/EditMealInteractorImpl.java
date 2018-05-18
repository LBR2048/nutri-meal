package ardjomand.leonardo.nutrimeal.data;

import android.net.Uri;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import ardjomand.leonardo.nutrimeal.meals.Meal;

/**
 * Created by leonardo on 12/05/2018.
 */

public class EditMealInteractorImpl implements EditMealInteractor.Interactor {

    public static final String NODE_MEALS = "meals";
    public static final String STORAGE_NODE_MEAL_IMAGES = "meal-images";
    public static final String GS = "gs://nutrimeal-b786e.appspot.com";

    private final DatabaseReference mealsRef;
    private final EditMealInteractor.Presenter presenter;
    private ValueEventListener mealEventListener;

    public EditMealInteractorImpl(EditMealInteractor.Presenter presenter) {
        this.presenter = presenter;

        mealsRef = FirebaseDatabase.getInstance().getReference()
                .child(NODE_MEALS);

    }

    @Override
    public void subscribeForMealUpdates(String key) {
        if (mealEventListener == null) {
            mealEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Meal meal = dataSnapshot.getValue(Meal.class);
                    if (meal != null) {
                        meal.setKey(dataSnapshot.getKey());
                        presenter.showMeal(meal);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // TODO show error
                }
            };
        }
        mealsRef.child(key).addValueEventListener(mealEventListener);
    }

    @Override
    public void unsubscribeFromMealUpdates() {
        if (mealEventListener != null) {
            mealsRef.removeEventListener(mealEventListener);
            mealEventListener = null;
        }
    }

    @Override
    public void updateMeal(Meal meal) {
        String key = meal.getKey();
        mealsRef.child(key).child("name").setValue(meal.getName());
        mealsRef.child(key).child("description").setValue(meal.getDescription());
        mealsRef.child(key).child("unitPrice").setValue(meal.getUnitPrice());
    }

    @Override
    public void updateMealImage(String key, Uri imageUri) {
        if (imageUri != null) {
            FirebaseStorage.getInstance().getReference().child(STORAGE_NODE_MEAL_IMAGES)
                    .child(key).putFile(imageUri);

            mealsRef.child(key).child("imagePath").setValue(GS + "/" + STORAGE_NODE_MEAL_IMAGES + "/" + key);
        }
    }
}
