package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.database.DatabaseReference;

import ardjomand.leonardo.nutrimeal.meals.Meal;
import ardjomand.leonardo.nutrimeal.meals.MealsPresenter;

public class MealRepositoryImpl implements MealRepository {

    private final MealsPresenter presenter;
    private final DatabaseReference mealsRef;

    public MealRepositoryImpl(MealsPresenter presenter) {
        this.presenter = presenter;

        FirebaseHelper firebaseHelper = new FirebaseHelper();

        mealsRef = firebaseHelper.getItemsRef(Meal.class);
    }

    @Override
    public String createMeal() {
        DatabaseReference newMealRef = mealsRef.push();
        return newMealRef.getKey();
    }
}
