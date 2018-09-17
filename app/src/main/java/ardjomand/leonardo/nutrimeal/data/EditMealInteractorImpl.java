package ardjomand.leonardo.nutrimeal.data;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import ardjomand.leonardo.nutrimeal.data.common.FirebaseHelper;
import ardjomand.leonardo.nutrimeal.data.pojos.Meal;

/**
 * Created by leonardo on 12/05/2018.
 */

public class EditMealInteractorImpl implements EditMealInteractor.Interactor {

    private static final String STORAGE_NODE_MEAL_IMAGES = "meal-images";
    private static final String GS = "gs://nutrimeal-b786e.appspot.com";

    private final DatabaseReference mealsRef;
    private final EditMealInteractor.Presenter presenter;

    public EditMealInteractorImpl(EditMealInteractor.Presenter presenter) {
        this.presenter = presenter;
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        mealsRef = firebaseHelper.getMealsRef();
    }

    @Override
    public void updateMeal(Meal meal) {
        String key = meal.getKey();
        mealsRef.child(key).child("name").setValue(meal.getName());
        mealsRef.child(key).child("description").setValue(meal.getDescription());
        mealsRef.child(key).child("unitPrice").setValue(meal.getUnitPrice());
    }

    @Override
    public void updateMealImage(final String key, Uri imageUri) {
        if (imageUri != null) {
            FirebaseStorage.getInstance().getReference().child(STORAGE_NODE_MEAL_IMAGES)
                    .child(key).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        mealsRef.child(key).child("imagePath").setValue(GS + "/" + STORAGE_NODE_MEAL_IMAGES + "/" + key);
                        mealsRef.child(key).child("imageLastModified").setValue(ServerValue.TIMESTAMP);
                    }
                }
            });
        }
    }

    @Override
    public void deleteMeal(String key) {
        mealsRef.child(key).setValue(null);
    }
}
