package ardjomand.leonardo.nutrimeal.auth.account

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import ardjomand.leonardo.nutrimeal.BuildConfig
import ardjomand.leonardo.nutrimeal.R
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.layout_progress.*

class AccountFragment : Fragment(),
        View.OnClickListener,
        AccountContract.View {

    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var presenter: AccountContract.Presenter

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = AccountPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
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
    override fun showProgressBar(visibility: Boolean) {
        progressBarLayout.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    override fun goToMainScreen() {
        mListener?.onRegistrationSuccess()
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    //endregion

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.signupButton -> {
                Toast.makeText(context, "Register", Toast.LENGTH_SHORT).show()
                val type: String = when {
                    BuildConfig.FLAVOR == "company" -> "company"
                    else -> "customer"
                }
                presenter.createAccount(
                        signupNameEditText.text.toString(),
                        signupEmailEditText.text.toString(),
                        signupPasswordEditText.text.toString(),
                        signupPassword2EditText.text.toString(),
                        type)
            }
        }
    }

    interface OnFragmentInteractionListener {
        fun onRegistrationSuccess()
    }

    companion object {

        fun newInstance() = AccountFragment()
    }
}
