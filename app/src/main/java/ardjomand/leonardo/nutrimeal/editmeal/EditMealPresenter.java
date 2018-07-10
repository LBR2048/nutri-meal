package ardjomand.leonardo.nutrimeal.editmeal;

import android.net.Uri;

import ardjomand.leonardo.nutrimeal.data.EditMealInteractor;
import ardjomand.leonardo.nutrimeal.data.EditMealInteractorImpl;
import ardjomand.leonardo.nutrimeal.meals.Meal;

/**
 * Created by leonardo on 12/05/2018.
 */

public class EditMealPresenter implements EditMealContract.Presenter, EditMealInteractor.Presenter {

    private EditMealContract.View view;
    private EditMealInteractor.Interactor interactor;

    EditMealPresenter(EditMealContract.View view) {
        this.view = view;
        interactor = new EditMealInteractorImpl(this);
    }

    @Override
    public void setView(EditMealContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribeForMealUpdates(String key) {
        interactor.subscribeForMealUpdates(key);
    }

    @Override
    public void unsubscribeFromMealUpdates() {
        interactor.unsubscribeFromMealUpdates();
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
    public void showMeal(Meal meal) {
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
