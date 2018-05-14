package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ardjomand.leonardo.nutrimeal.meals.Meal;

/**
 * Created by leonardo on 12/05/2018.
 */

public class EditMealInteractorImpl implements EditMealInteractor.Interactor {

    public static final String NODE_MEALS = "meals";

    private final DatabaseReference mealsRef;
    private final EditMealInteractor.Presenter presenter;

    public EditMealInteractorImpl(EditMealInteractor.Presenter presenter) {
        this.presenter = presenter;

        mealsRef = FirebaseDatabase.getInstance().getReference()
                .child(NODE_MEALS);

    }

    @Override
    public void getMeal(String key) {
        mealsRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Meal meal = dataSnapshot.getValue(Meal.class);
                meal.setKey(dataSnapshot.getKey());
                presenter.showMeal(meal);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO show error
            }
        });
    }

    @Override
    public void updateMeal(Meal meal) {
        String key = meal.getKey();
        meal.setKey(null);
        mealsRef.child(key).setValue(meal);
    }
}
