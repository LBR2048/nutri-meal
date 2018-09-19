package ardjomand.leonardo.nutrimeal.auth.login

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import ardjomand.leonardo.nutrimeal.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.layout_progress.*

class LoginFragment : Fragment(),
        View.OnClickListener,
        LoginContract.View {

    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var presenter: LoginContract.Presenter

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = LoginPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.getCurrentUser()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.setView(null)
    }
    //endregion

    //region Presenter callbacks
    override fun showLoginForm(visibility: Boolean) {
        loginLayout.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    override fun showProgressBar(visibility: Boolean) {
        progressBarLayout.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    override fun goToMainScreen() {
        mListener?.onLoginSuccess()
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    //endregion

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.login_button -> {
                presenter.logIn(
                        loginEmailEditText.text.toString(),
                        loginPasswordEditText.text.toString())
            }

            R.id.signupText -> mListener?.onCreateAccountClicked()
        }
    }

    interface OnFragmentInteractionListener {
        fun onCreateAccountClicked()
        fun onLoginSuccess()
    }

    companion object {

        fun newInstance() = LoginFragment()
    }
}
