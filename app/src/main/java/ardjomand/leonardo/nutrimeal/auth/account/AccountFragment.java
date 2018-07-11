package ardjomand.leonardo.nutrimeal.auth.account;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import ardjomand.leonardo.nutrimeal.BuildConfig;
import ardjomand.leonardo.nutrimeal.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment implements AccountContract.View{

    //region Views
    @BindView(R.id.signup_name_edit_text)
    TextInputEditText signupNameEditText;
    @BindView(R.id.signup_email_edit_text)
    TextInputEditText signupEmailEditText;
    @BindView(R.id.signup_password_edit_text)
    TextInputEditText signupPasswordEditText;
    @BindView(R.id.signup_password2_edit_text)
    TextInputEditText signupPassword2EditText;
    @BindView(R.id.progress_bar_layout)
    FrameLayout progressBarLayout;
    private Unbinder unbinder;
    //endregion

    private OnFragmentInteractionListener mListener;
    private AccountPresenter presenter;

    //region Constructors
    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }
    //endregion

    //region Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new AccountPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.setView(null);
    }

    //endregion

    @OnClick(R.id.signup_button)
    public void onViewClicked() {
        Toast.makeText(getContext(), "Register", Toast.LENGTH_SHORT).show();

        String type;
        if (BuildConfig.FLAVOR.equals("company")) {
            type = "company";
        } else {
            type = "customer";
        }

        presenter.createAccount(
                signupNameEditText.getText().toString(),
                signupEmailEditText.getText().toString(),
                signupPasswordEditText.getText().toString(),
                signupPassword2EditText.getText().toString(),
                type);
    }

    @Override
    public void showProgressBar(boolean visibility) {
        progressBarLayout.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void goToMainScreen() {
        mListener.onRegistrationSuccess();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onRegistrationSuccess();
    }
}
