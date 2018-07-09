package ardjomand.leonardo.nutrimeal.meals;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.EditCartInteractor;
import ardjomand.leonardo.nutrimeal.data.EditCartInteractorImpl;
import ardjomand.leonardo.nutrimeal.data.MealRepository;
import ardjomand.leonardo.nutrimeal.data.MealRepositoryImpl;

public class MealsPresenter implements MealsContract.Presenter, MealRepository.Presenter {

    public static final String TAG = MealsPresenter.class.getSimpleName();
    private final EditCartInteractor.Interactor editCartInteractor;
    private final MealRepository.Repository repository;
    private MealsContract.View view;

    MealsPresenter(MealsContract.View view) {
        this.view = view;
        repository = new MealRepositoryImpl(this);
        editCartInteractor = new EditCartInteractorImpl(this);
    }

    @Override
    public void setView(MealsContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribeToMealsUpdates() {
        repository.subscribeForMealUpdates();
//        view.showMeals(DummyMeals.ITEMS);
    }

    @Override
    public void unsubscribeFromMealsUpdates() {
        repository.unsubscribeFromMealUpdates();
    }

    @Override
    public void addMealToCart(Meal meal) {
        Log.i(TAG, "Adding " + meal.getName() + " to cart");
        editCartInteractor.addMealToCart(meal);
    }

    @Override
    public void editMeal(String key) {
        if (view != null){
            view.goToEditMeal(key);
        }
    }

    @Override
    public void editCartMealQuantity(String key) {
        if (view != null){
            view.goToEditCartMealQuantity(key);
        }
    }

    @Override
    public void createNewMeal() {
        String key = repository.createMeal();
        if (view != null){
            view.goToEditMeal(key);
        }
    }

    @Override
    public void onMealAdded(Meal meal) {
        Log.i(TAG, "Meal " + meal.getName() + " added");
        if (view != null){
            view.addMeal(meal);
        }
    }

    @Override
    public void onMealChanged(Meal meal) {
        Log.i(TAG, "Meal " + meal.getName() + " changed");
        if (view != null){
            view.updateMeal(meal);
        }
    }

    @Override
    public void onMealRemoved(String key) {
        Log.i(TAG, "Meal removed");
    }
}
