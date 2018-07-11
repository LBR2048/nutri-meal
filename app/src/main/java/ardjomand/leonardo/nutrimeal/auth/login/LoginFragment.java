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
    private Unbinder unbinder;
    //endregion

    private OnFragmentInteractionListener mListener;
    private LoginPresenter presenter;

    //region Constructors
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new LoginPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.setView(null);
    }
    //endregion

    @OnClick({R.id.login_button, R.id.signup_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
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

    public interface OnFragmentInteractionListener {
        void onCreateAccountClicked();
        void onLoginSuccess();
    }
}
