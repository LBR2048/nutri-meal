package ardjomand.leonardo.nutrimeal.editmeal;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ardjomand.leonardo.nutrimeal.R;
import ardjomand.leonardo.nutrimeal.meals.Meal;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditMealFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditMealFragment extends Fragment implements EditMealContract.View {

    public static final String STATE_KEY = "state-key";
    private static final String ARG_KEY = "arg-meal";
    //region Views
    @BindView(R.id.edit_meal_image)
    ImageView editMealImage;
    @BindView(R.id.edit_meal_name)
    EditText editMealName;
    @BindView(R.id.edit_meal_description)
    EditText editMealDescription;
    @BindView(R.id.edit_meal_price)
    EditText editMealPrice;
    Unbinder unbinder;
    //endregion

    private EditMealPresenter editMealPresenter;
    private Meal meal;
    private String key;


    public EditMealFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditMealFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditMealFragment newInstance(String param1, String param2) {
        EditMealFragment fragment = new EditMealFragment();
        Bundle args = new Bundle();
        args.putString(ARG_KEY, param1);
        fragment.setArguments(args);
        return fragment;
    }

    //region Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            key = getArguments().getString(ARG_KEY);
        }

        editMealPresenter = new EditMealPresenter(this);

        if (savedInstanceState == null) {
            editMealPresenter.getMeal(key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_meal, container, false);
        unbinder = ButterKnife.bind(this, view);

        setTitle();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_KEY, key);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            key = savedInstanceState.getString(STATE_KEY);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    //endregion

    //region Presenter callbacks
    @Override
    public void showMeal(Meal meal) {
        key = meal.getKey();
        editMealName.setText(meal.getName());
        editMealDescription.setText(meal.getDescription());

        // TODO add $ as a label
//        NumberFormat format = NumberFormat.getCurrencyInstance();
//        editMealPrice.setText(format.format(meal.getUnitPrice()));
        editMealPrice.setText(String.valueOf(meal.getUnitPrice()));
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
                supportActionBar.setTitle(R.string.edit_meal_title);
            }
        }
    }

    public void updateMeal() {
        Meal updatedMeal = new Meal(key, editMealName.getText().toString(),
                editMealDescription.getText().toString(), "",
                Long.parseLong(editMealPrice.getText().toString()), true);
        editMealPresenter.updateMeal(updatedMeal);
    }

    @OnClick(R.id.edit_meal_image)
    public void onViewClicked() {
        Toast.makeText(getContext(), "Image clicked", Toast.LENGTH_SHORT).show();
    }
}
