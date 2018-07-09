package ardjomand.leonardo.nutrimeal.meals;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ardjomand.leonardo.nutrimeal.BuildConfig;
import ardjomand.leonardo.nutrimeal.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnMealFragmentInteractionListener}
 * interface.
 */
public class MealsFragment extends Fragment implements
        MealsContract.View,
        MealAdapter.OnMealAdapterInteractionListener {

    private static final String ARG_COLUMN_COUNT = "column-count";

    //region Views
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.meals_add_fab)
    FloatingActionButton addMeal;
    //endregion

    //region Member variables
    private int mColumnCount = 1;
    private OnMealFragmentInteractionListener mListener;
    private Unbinder unbinder;
    private MealsPresenter presenter;
    private MealAdapter adapter;
    //endregion

    //region Constructors
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MealsFragment() {
    }

    @SuppressWarnings("unused")
    public static MealsFragment newInstance(int columnCount) {
        MealsFragment fragment = new MealsFragment();
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
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        presenter = new MealsPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals, container, false);
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
        adapter = new MealAdapter(new ArrayList<Meal>(), this, getContext());
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
        if (context instanceof OnMealFragmentInteractionListener) {
            mListener = (OnMealFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrdersFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.clearData();
        presenter.subscribeToMealsUpdates();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribeFromMealsUpdates();
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

    //region Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_meals_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_orders:
                mListener.onGoToOrdersClicked();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region Presenter callbacks
    @Override
    public void showMeals(List<Meal> meals) {
        adapter.replaceData(meals);
    }

    @Override
    public void addMeal(Meal meal) {
        adapter.addData(meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        adapter.updateData(meal);
    }

    @Override
    public void showEmptyMeals() {
        Toast.makeText(getActivity(), "No meals available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToEditMeal(String key) {
        mListener.onEditMealClicked(key);
    }

    @Override
    public void goToEditCartMealQuantity(String key) {
        mListener.onEditCartMealQuantity(key);
    }
//endregion

    private void setTitle() {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(R.string.meals_title);
            }
        }
    }

    @OnClick({R.id.button, R.id.meals_add_fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                mListener.onGoToCartClicked();
                break;

            case R.id.meals_add_fab:
                presenter.createNewMeal();
                break;
        }
    }

    @Override
    public void onMealClicked(Meal meal) {
        if (BuildConfig.FLAVOR.equals("company")) {
            presenter.editMeal(meal.getKey());
        } else {
            // TODO add meal to cart only if item quantity in cart is zero
            presenter.addMealToCart(meal);
            presenter.editCartMealQuantity(meal.getKey());
            // TODO only show toast if meal was successfully added to cart
//            Toast.makeText(getContext(), meal.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnMealFragmentInteractionListener {
        void onEditMealClicked(String key);
        void onEditCartMealQuantity(String key);
        void onGoToCartClicked();
        void onGoToOrdersClicked();
    }
}
