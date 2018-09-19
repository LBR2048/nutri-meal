package ardjomand.leonardo.nutrimeal.auth.account

internal interface AccountContract {

    interface Presenter {

        fun setView(view: View?)

        fun createAccount(name: String,
                          email: String,
                          password: String,
                          repeatedPassword: String,
                          type: String)

        fun getCurrentUser()
    }

    interface View {

        fun showProgressBar(visibility: Boolean)

        fun goToMainScreen()

        fun showError(message: String)
    }
}
