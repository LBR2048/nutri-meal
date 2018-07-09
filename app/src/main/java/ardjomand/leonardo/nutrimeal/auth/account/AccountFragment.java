package ardjomand.leonardo.nutrimeal.auth.account;

import android.content.Context;
import android.os.Bundle;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
    Unbinder unbinder;
    //endregion

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private AccountPresenter presenter;

    //region Constructors
    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        presenter = new AccountPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
        presenter.setView(null);
        unbinder.unbind();
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
