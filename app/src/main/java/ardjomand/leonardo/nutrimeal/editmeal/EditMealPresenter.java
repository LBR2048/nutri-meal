package ardjomand.leonardo.nutrimeal.editmeal;

import ardjomand.leonardo.nutrimeal.data.EditMealInteractor;
import ardjomand.leonardo.nutrimeal.data.EditMealInteractorImpl;
import ardjomand.leonardo.nutrimeal.meals.Meal;

/**
 * Created by leonardo on 12/05/2018.
 */

public class EditMealPresenter implements EditMealContract.Presenter, EditMealInteractor.Presenter {

    private EditMealContract.View view;
    private EditMealInteractor.Interactor interactor;

    public EditMealPresenter(EditMealContract.View view) {
        this.view = view;
        interactor = new EditMealInteractorImpl(this);
    }

    @Override
    public void getMeal(String key) {
        interactor.getMeal(key);
    }

    @Override
    public void updateMeal(Meal meal) {
        interactor.updateMeal(meal);
    }

    @Override
    public void showMeal(Meal meal) {
        view.showMeal(meal);
    }
}
