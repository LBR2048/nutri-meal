package ardjomand.leonardo.nutrimeal.cart;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;

import ardjomand.leonardo.nutrimeal.GlideApp;
import ardjomand.leonardo.nutrimeal.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class EditCartMealDialogFragment extends DialogFragment implements EditCartMealContract.View {

    private static final String ARG_CART_MEAL_KEY = "arg_cart_meal_key";

    //region GUI
    @BindView(R.id.edit_cart_meal_name)
    TextView nameView;
    @BindView(R.id.edit_cart_meal_image)
    ImageView imageView;
    @BindView(R.id.edit_cart_meal_quantity)
    TextView quantityView;
    private Unbinder unbinder;
    //endregion

    private String mCartMealKey;
    private EditCartMealContract.Presenter presenter;
    private CartMeal cartMeal;

    public EditCartMealDialogFragment() {
        // Required empty public constructor
    }

    public static EditCartMealDialogFragment newInstance(String key) {
        EditCartMealDialogFragment fragment = new EditCartMealDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CART_MEAL_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    public static void showDialog(AppCompatActivity activity, String key, String tag) {
        newInstance(key).show(activity.getSupportFragmentManager(), tag);
    }

    //region Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCartMealKey = getArguments().getString(ARG_CART_MEAL_KEY);
        }

        presenter = new EditCartMealDialogPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_cart_meal_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribeForCartMealsUpdates(mCartMealKey);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribeFromCartMealsUpdates();
    }
    //endregion

    @Override
    public void updateCartMeal(CartMeal cartMeal) {
        this.cartMeal = cartMeal;
        nameView.setText(cartMeal.getName());
        quantityView.setText(NumberFormat.getInstance().format(cartMeal.getQuantity()));

        String imagePath = cartMeal.getImagePath();
        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imagePath);

        if (getContext() != null) {
            GlideApp.with(getContext())
                    .load(imageRef)
                    .apply(RequestOptions.centerCropTransform().fallback(R.mipmap.ic_launcher))
                    .into(imageView);
        }
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

    @OnClick({R.id.edit_cart_meal_increase, R.id.edit_cart_meal_decrease})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_cart_meal_increase:
                cartMeal.increaseQuantity();
                presenter.editCartMeal(cartMeal);
                break;
            case R.id.edit_cart_meal_decrease:
                cartMeal.decreaseQuantity();
                presenter.editCartMeal(cartMeal);
                break;
        }
    }
}
