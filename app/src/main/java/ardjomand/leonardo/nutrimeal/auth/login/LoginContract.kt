package ardjomand.leonardo.nutrimeal.auth.login

internal interface LoginContract {

    interface Presenter {

        fun setView(view: View?)

        fun logIn(email: String, password: String)

        fun getCurrentUser()
    }

    interface View {

        fun showProgressBar(visibility: Boolean)

        fun showLoginForm(visibility: Boolean)

        fun goToMainScreen()

        fun showError(message: String)
    }
}
