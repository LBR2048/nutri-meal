package ardjomand.leonardo.nutrimeal.customerorders;

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
import ardjomand.leonardo.nutrimeal.widget.OrdersWidget;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CustomerOrdersFragment extends Fragment implements
        CustomerOrdersContract.View,
        CustomerOrdersAdapter.OnMealAdapterInteractionListener {

    private static final String ARG_COLUMN_COUNT = "column-count";

    //region Views
    @BindView(R.id.list)
    RecyclerView recyclerView;
    //endregion

    //region Member variables
    private int mColumnCount = 1;
    private Unbinder unbinder;
    private CustomerOrdersContract.Presenter presenter;
    private CustomerOrdersAdapter adapter;
    //endregion

    //region Constructors
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CustomerOrdersFragment() {
    }

    @SuppressWarnings("unused")
    public static CustomerOrdersFragment newInstance(int columnCount) {
        CustomerOrdersFragment fragment = new CustomerOrdersFragment();
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

        presenter = new CustomerOrdersPresenter(this);
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
        adapter = new CustomerOrdersAdapter(new ArrayList<CustomerOrder>(), this, getContext());
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
        presenter.subscribeToOrdersUpdates();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribeFromOrdersUpdates();
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
    public void addOrder(CustomerOrder customerOrder) {
        adapter.addData(customerOrder);
        OrdersWidget.updateAllWidgets(getContext());
    }

    @Override
    public void updateOrder(CustomerOrder customerOrder) {
        adapter.updateData(customerOrder);
        OrdersWidget.updateAllWidgets(getContext());
    }

    @Override
    public void showEmptyOrder() {
        Toast.makeText(getActivity(), "Cart is empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
    public void onOrderClicked(CustomerOrder customerOrder) {
//        presenter.addMealToCart(meal);
    }
}
