package ardjomand.leonardo.nutrimeal.cart

import ardjomand.leonardo.nutrimeal.data.pojos.CartMeal

internal interface EditCartMealContract {

    interface View {

        fun updateCartMeal(cartMeal: CartMeal)
    }

    interface Presenter {

        fun setView(view: View?)

        fun subscribe(key: String)

        fun unsubscribe()

        fun increaseQuantity()

        fun decreaseQuantity()
    }
}
