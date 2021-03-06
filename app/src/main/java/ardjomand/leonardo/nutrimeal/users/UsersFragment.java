package ardjomand.leonardo.nutrimeal.users;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ardjomand.leonardo.nutrimeal.R;
import ardjomand.leonardo.nutrimeal.data.pojos.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UsersFragment extends Fragment implements
        UsersContract.View,
        UsersAdapter.OnMealAdapterInteractionListener {

    private static final String ARG_COLUMN_COUNT = "column-count";

    //region Views
    @BindView(R.id.list)
    RecyclerView recyclerView;
    //endregion

    //region Member variables
    private int mColumnCount = 1;
    private Unbinder unbinder;
    private UsersContract.Presenter presenter;
    private UsersAdapter adapter;
    //endregion

    //region Constructors
    public UsersFragment() {
    }

    @SuppressWarnings("unused")
    public static UsersFragment newInstance(int columnCount) {
        UsersFragment fragment = new UsersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        presenter = new UsersPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        unbinder = ButterKnife.bind(this, view);

        setTitle();

        // Set layout manager
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        // Set adapter
        adapter = new UsersAdapter(new ArrayList<User>(), this, getContext());
        recyclerView.setAdapter(adapter);

        // Set decoration
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.clearData();
        presenter.subscribeToUsersUpdates();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribeFromUsersUpdates();
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

    //region Presenter callbacks
    @Override
    public void addUser(User user) {
        adapter.addData(user);
    }

    @Override
    public void updateUser(User user) {
        adapter.updateData(user);
    }

    @Override
    public void showEmptyUser() {
        Toast.makeText(getActivity(), R.string.error_no_items_available, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), R.string.error_items_could_not_be_downloaded, Toast.LENGTH_SHORT).show();
    }
    //endregion

    private void setTitle() {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(R.string.orders_title);
            }
        }
    }

    @Override
    public void onUserClicked(User item) {
        Toast.makeText(getContext(), item.getName() + " clicked", Toast.LENGTH_SHORT).show();
    }

}
