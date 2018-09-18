package ardjomand.leonardo.nutrimeal.meals;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.EditCartInteractor;
import ardjomand.leonardo.nutrimeal.data.EditCartInteractorImpl;
import ardjomand.leonardo.nutrimeal.data.MealRepository;
import ardjomand.leonardo.nutrimeal.data.MealRepositoryImpl;
import ardjomand.leonardo.nutrimeal.data.common.GenericRepository;
import ardjomand.leonardo.nutrimeal.data.common.GenericRepositoryImpl;
import ardjomand.leonardo.nutrimeal.data.pojos.Meal;

public class MealsPresenter implements
        MealsContract.Presenter,
        GenericRepository.Presenter<Meal> {

    private static final String TAG = MealsPresenter.class.getSimpleName();
    private final EditCartInteractor editCartInteractor;
    private final MealRepository repository;
    private MealsContract.View view;
    private final GenericRepositoryImpl<Meal> genericRepository;

    MealsPresenter(MealsContract.View view) {
        this.view = view;
        repository = new MealRepositoryImpl(this);
        genericRepository = new GenericRepositoryImpl<>(this, Meal.class);
        editCartInteractor = new EditCartInteractorImpl(this);
    }

    @Override
    public void setView(MealsContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        genericRepository.subscribe();
//        view.showItems(DummyMeals.ITEMS);
    }

    @Override
    public void unsubscribe() {
        genericRepository.unsubscribe();
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
    public void onItemAdded(Meal meal) {
        Log.i(TAG, "Meal " + meal.getName() + " added");
        if (view != null){
            view.addItem(meal);
        }
    }

    @Override
    public void onItemChanged(Meal meal) {
        Log.i(TAG, "Meal " + meal.getName() + " changed");
        if (view != null){
            view.updateItem(meal);
        }
    }

    @Override
    public void onItemRemoved(String key) {
        Log.i(TAG, "Meal removed");
    }
}
