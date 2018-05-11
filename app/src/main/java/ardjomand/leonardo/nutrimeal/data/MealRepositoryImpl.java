package ardjomand.leonardo.nutrimeal.data;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ardjomand.leonardo.nutrimeal.meals.Meal;
import ardjomand.leonardo.nutrimeal.meals.MealsPresenter;

public class MealRepositoryImpl implements MealRepository.Repository {

    public static final String TAG = MealRepositoryImpl.class.getSimpleName();
    public static final String NODE_MEALS = "meals";

    private MealsPresenter presenter;
    private DatabaseReference mealsRef;
    private ChildEventListener mealsEventListener;

    public MealRepositoryImpl(MealsPresenter presenter) {
        this.presenter = presenter;

        mealsRef = FirebaseDatabase.getInstance().getReference().child(NODE_MEALS);
    }

    @Override
    public void subscribeForMealUpdates() {

        if (mealsEventListener == null) {
            mealsEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Meal meal = dataSnapshot.getValue(Meal.class);
                    meal.setKey(dataSnapshot.getKey());
                    presenter.onMealAdded(meal);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Meal meal = dataSnapshot.getValue(Meal.class);
                    meal.setKey(dataSnapshot.getKey());
                    presenter.onMealChanged(meal);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    presenter.onMealRemoved(dataSnapshot.getKey());
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mealsRef.addChildEventListener(mealsEventListener);
            Log.i(TAG, "Subscribing to meal updates");
        }
    }

    @Override
    public void unsubscribeFromMealUpdates() {
        if (mealsEventListener != null) {
            mealsRef.removeEventListener(mealsEventListener);
            mealsEventListener = null;
            Log.i(TAG, "Unsubscribing from meal updates");
        }
    }
}
