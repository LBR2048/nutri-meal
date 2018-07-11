package ardjomand.leonardo.nutrimeal.editmeal;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import ardjomand.leonardo.nutrimeal.GlideApp;
import ardjomand.leonardo.nutrimeal.R;
import ardjomand.leonardo.nutrimeal.meals.Meal;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditMealFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditMealFragment extends Fragment implements
        EditMealContract.View {

    private static final String STATE_KEY = "state-key";
    private static final String ARG_KEY = "arg-meal";
    private static final int PICK_IMAGE_CODE = 1046;

    //region Views
    @BindView(R.id.edit_meal_image)
    ImageView editMealImage;
    @BindView(R.id.edit_meal_name)
    TextInputEditText editMealName;
    @BindView(R.id.edit_meal_description)
    TextInputEditText editMealDescription;
    @BindView(R.id.edit_meal_price)
    TextInputEditText editMealPrice;
    private Unbinder unbinder;
    //endregion

    private EditMealContract.Presenter presenter;
    private String key;

    public EditMealFragment() {
        // Required empty public constructor
    }

    public static EditMealFragment newInstance(String param1) {
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
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            key = getArguments().getString(ARG_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_meal, container, false);
        unbinder = ButterKnife.bind(this, view);

        setTitle();

        presenter = new EditMealPresenter(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.subscribeForMealUpdates(key);
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
        presenter.unsubscribeFromMealUpdates();
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
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_edit_meal_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_meal_delete:
                presenter.deleteMeal(key);
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
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
    public void showMealImage(String imagePath) {
        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imagePath);

        if (getContext() != null) {
            GlideApp.with(getContext())
                    .load(imageRef)
                    .apply(RequestOptions.centerCropTransform().fallback(R.mipmap.ic_launcher))
                    .into(editMealImage);
        }
    }

    @Override
    public void showAddMealImageIcon() {
        editMealImage.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), R.string.error_items_could_not_be_downloaded, Toast.LENGTH_SHORT).show();
    }
    //endregion

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE_CODE) {
                Uri imageUri = data.getData();
                presenter.updateMealImage(key, imageUri);
            }
        }
    }

    //region Helper methods
    private void pickPhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (getActivity() != null) {
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent, PICK_IMAGE_CODE);
            }
        }
    }

    private void setTitle() {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                // TODO change title to Add new meal if the FAB is clicked
                supportActionBar.setTitle(R.string.edit_meal_title);
            }
        }
    }

    private void updateMeal() {
        String name = editMealName.getText().toString();
        String description = editMealDescription.getText().toString();
        String unitPriceString = editMealPrice.getText().toString();

        long unitPrice;
        if (!unitPriceString.isEmpty()) {
            unitPrice = Long.parseLong(unitPriceString);
        } else {
            unitPrice = 0;
        }

        Meal updatedMeal = new Meal(key, name, description, "", unitPrice, true);
        presenter.updateMeal(updatedMeal);
    }
    //endregion

    @OnClick(R.id.edit_meal_image)
    public void onViewClicked() {
        Toast.makeText(getContext(), "Image clicked", Toast.LENGTH_SHORT).show();

        pickPhotoFromGallery();
    }

    @OnFocusChange({R.id.edit_meal_name, R.id.edit_meal_description, R.id.edit_meal_price})
    public void onFocusChange(boolean focused) {
        if (!focused) {
            updateMeal();
        }
    }
}
