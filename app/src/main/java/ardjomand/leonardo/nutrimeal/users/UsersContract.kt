package ardjomand.leonardo.nutrimeal.users

import ardjomand.leonardo.nutrimeal.data.pojos.User

internal interface UsersContract {

    interface View {

        fun addUser(user: User)

        fun updateUser(user: User)

        fun showEmptyUser()

        fun showError()
    }

    interface Presenter {

        fun setView(view: View?)

        fun subscribeToUsersUpdates()

        fun unsubscribeFromUsersUpdates()
    }
}
