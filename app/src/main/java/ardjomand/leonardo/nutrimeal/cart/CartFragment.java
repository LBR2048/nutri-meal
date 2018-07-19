package ardjomand.leonardo.nutrimeal.cart;

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
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import ardjomand.leonardo.nutrimeal.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnOrderedMealFragmentInteractionListener}
 * interface.
 */
public class CartFragment extends Fragment implements CartContract.View {

    private static final String ARG_COLUMN_COUNT = "column-count";

    //region Views
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    //endregion

    //region Member variables
    private int mColumnCount = 1;
    private OnOrderedMealFragmentInteractionListener mListener;
    private Unbinder unbinder;
    private CartContract.Presenter presenter;
    private CartMealAdapter adapter;
    //endregion

    //region Constructors
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CartFragment() {
    }

    @SuppressWarnings("unused")
    public static CartFragment newInstance(int columnCount) {
        CartFragment fragment = new CartFragment();
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

        presenter = new CartPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, view);

        setTitle();

        // Set the adapter
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapter = new CartMealAdapter(new ArrayList<CartMeal>(), mListener, getContext());
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOrderedMealFragmentInteractionListener) {
            mListener = (OnOrderedMealFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrdersFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.clearData();
        presenter.subscribeToCartUpdates();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribeFromCartUpdates();
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

    //region Presenter callbacks
    @Override
    public void addCartMeal(CartMeal cartMeal) {
        adapter.addData(cartMeal);
    }

    @Override
    public void updateCartMeal(CartMeal cartMeal) {
        adapter.updateData(cartMeal);
    }

    @Override
    public void showEmptyMeals() {
        Toast.makeText(getActivity(), R.string.error_no_items_available, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), R.string.error_no_items_available, Toast.LENGTH_SHORT).show();
    }
    //endregion

    // TODO move the responsibility of setting title to the parent activity
    // this will make it easier for the fragments to be added instead of replaced
    private void setTitle() {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(R.string.cart_title);
                supportActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        presenter.placeOrder();
        // TODO only call next line if order was placed successfully
        mListener.onPlaceOrderClicked();
    }

    public interface OnOrderedMealFragmentInteractionListener {
        void onOrderedMealClicked(CartMeal item);
        void onPlaceOrderClicked();
    }
}
