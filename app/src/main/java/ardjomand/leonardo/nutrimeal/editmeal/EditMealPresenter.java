package ardjomand.leonardo.nutrimeal.editmeal;

import android.net.Uri;

import ardjomand.leonardo.nutrimeal.data.EditMealInteractor;
import ardjomand.leonardo.nutrimeal.data.EditMealInteractorImpl;
import ardjomand.leonardo.nutrimeal.data.GenericItemRepository;
import ardjomand.leonardo.nutrimeal.data.GenericItemRepositoryImpl;
import ardjomand.leonardo.nutrimeal.meals.Meal;

/**
 * Created by leonardo on 12/05/2018.
 */

public class EditMealPresenter implements
        EditMealContract.Presenter,
        EditMealInteractor.Presenter,
        GenericItemRepository.Presenter<Meal> {

    private final GenericItemRepository.Repository genericItemRepository;
    private EditMealContract.View view;
    private final EditMealInteractor.Interactor interactor;

    EditMealPresenter(EditMealContract.View view, String key) {
        this.view = view;
        genericItemRepository = new GenericItemRepositoryImpl<>(this, Meal.class, key);
        interactor = new EditMealInteractorImpl(this);
    }

    @Override
    public void setView(EditMealContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribeForMealUpdates(String key) {
        genericItemRepository.subscribe(key);
    }

    @Override
    public void unsubscribeFromMealUpdates() {
        genericItemRepository.unsubscribe();
    }

    @Override
    public void updateMeal(Meal meal) {
        interactor.updateMeal(meal);
    }

    @Override
    public void updateMealImage(String key, Uri imageUri) {
        interactor.updateMealImage(key, imageUri);
    }

    @Override
    public void onItemRead(Meal meal) {
        if (view != null) {
            view.showMeal(meal);
            if (meal.getImagePath() != null && !meal.getImagePath().isEmpty()) {
                view.showMealImage(meal.getImagePath());
            } else {
                view.showAddMealImageIcon();
            }
        }
    }

    @Override
    public void deleteMeal(String key) {
        interactor.deleteMeal(key);
    }
}
