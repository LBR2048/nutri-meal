package ardjomand.leonardo.nutrimeal.auth.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements LoginContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //region Views
    @BindView(R.id.login_email_edit_text)
    TextInputEditText loginEmailEditText;
    @BindView(R.id.login_password_edit_text)
    TextInputEditText loginPasswordEditText;
    @BindView(R.id.signup_text)
    TextView signupText;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;
    @BindView(R.id.progress_bar_layout)
    FrameLayout progressBarLayout;
    Unbinder unbinder;
    //endregion

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private LoginPresenter presenter;

    //region Constructors
    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

        presenter = new LoginPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.getCurrentUser();
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

    @OnClick({R.id.login_button, R.id.signup_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                Toast.makeText(getContext(), "Login clicked", Toast.LENGTH_SHORT).show();
                presenter.logIn(
                        loginEmailEditText.getText().toString(),
                        loginPasswordEditText.getText().toString());
                break;
            case R.id.signup_text:
                mListener.onCreateAccountClicked();
                break;
        }
    }

    @Override
    public void showLoginForm(boolean visibility) {
        loginLayout.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProgressBar(boolean visibility) {
        progressBarLayout.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void goToMainScreen() {
        mListener.onLoginSuccess();
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
        void onCreateAccountClicked();

        void onLoginSuccess();
    }
}
