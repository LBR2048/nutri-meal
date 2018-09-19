package ardjomand.leonardo.nutrimeal.data

import android.net.Uri

import ardjomand.leonardo.nutrimeal.data.pojos.Meal

interface EditMealInteractor {

    interface Interactor {

        fun updateMeal(meal: Meal)

        fun updateMealImage(key: String, imageUri: Uri)

        fun deleteMeal(key: String)
    }

    interface Presenter {

        fun onItemRead(meal: Meal)
    }

}
