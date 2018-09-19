package ardjomand.leonardo.nutrimeal.cart

import ardjomand.leonardo.nutrimeal.data.pojos.CartMeal
import ardjomand.leonardo.nutrimeal.data.pojos.Meal

internal interface CartContract {

    interface View {

        fun addCartMeal(meal: CartMeal)

        fun updateCartMeal(meal: CartMeal)

        fun showEmptyMeals()

        fun showError()
    }

    interface Presenter {

        fun setView(view: View)

        fun subscribeToCartUpdates()

        fun unsubscribeFromCartUpdates()

        fun addMealToCart(meal: Meal)

        fun placeOrder()
    }
}
