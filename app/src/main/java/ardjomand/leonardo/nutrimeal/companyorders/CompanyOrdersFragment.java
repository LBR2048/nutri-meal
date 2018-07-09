package ardjomand.leonardo.nutrimeal.companyorders;

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
import ardjomand.leonardo.nutrimeal.meals.Meal;
import ardjomand.leonardo.nutrimeal.widget.OrdersWidget;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnOrdersFragmentInteractionListener}
 * interface.
 */
public class CompanyOrdersFragment extends Fragment implements
        CompanyOrdersContract.View,
        CompanyOrdersAdapter.OnMealAdapterInteractionListener {

    private static final String ARG_COLUMN_COUNT = "column-count";

    //region Views
    @BindView(R.id.list)
    RecyclerView recyclerView;
    //endregion

    //region Member variables
    private int mColumnCount = 1;
    private OnOrdersFragmentInteractionListener mListener;
    private Unbinder unbinder;
    private CompanyOrdersPresenter presenter;
    private CompanyOrdersAdapter adapter;
    //endregion

    //region Constructors
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CompanyOrdersFragment() {
    }

    @SuppressWarnings("unused")
    public static CompanyOrdersFragment newInstance(int columnCount) {
        CompanyOrdersFragment fragment = new CompanyOrdersFragment();
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

        presenter = new CompanyOrdersPresenter(this);
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
        adapter = new CompanyOrdersAdapter(new ArrayList<CompanyOrder>(), this, getContext());
        recyclerView.setAdapter(adapter);

        // Set decoration
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // TODO Orders fragment has no need to communicate user interaction to Activity
//        if (context instanceof OnOrdersFragmentInteractionListener) {
//            mListener = (OnOrdersFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnOrdersFragmentInteractionListener");
//        }
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

    //region Presenter callbacks
    @Override
    public void addOrder(CompanyOrder companyOrder) {
        adapter.addData(companyOrder);
        Toast.makeText(getContext(), "Updating widgets", Toast.LENGTH_SHORT).show();
        OrdersWidget.updateAllWidgets(getContext());
    }

    @Override
    public void updateOrder(CompanyOrder companyOrder) {
        adapter.updateData(companyOrder);
        Toast.makeText(getContext(), "Updating widgets", Toast.LENGTH_SHORT).show();
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
    public void onOrderClicked(CompanyOrder companyOrder) {
//        presenter.addMealToCart(meal);
    }

    public interface OnOrdersFragmentInteractionListener {
        void onMealClicked(Meal item);
        void onPlaceOrdersClicked();
    }
}
