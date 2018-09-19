package ardjomand.leonardo.nutrimeal.data

import ardjomand.leonardo.nutrimeal.data.pojos.User

interface AuthRepository {

    interface Interactor {

        interface CreateAccountCallback {

            fun onSuccess()

            fun onFailure(e: Exception)
        }

        fun createAccount(createAccountCallback: CreateAccountCallback,
                          user: User,
                          password: String,
                          type: String)


        interface LogInCallback {

            fun onSuccess()

            fun onFailure(e: Exception)
        }

        fun logIn(logInCallback: LogInCallback,
                  email: String,
                  password: String)


        interface GetCurrentUserCallback {

            fun onUserLoggedIn(user: User)

            fun onUserLoggedOut()

            fun onFailure(e: Exception)
        }

        fun getCurrentUser(getCurrentUserCallback: GetCurrentUserCallback)

    }

    interface Presenter
}
