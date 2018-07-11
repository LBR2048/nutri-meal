package ardjomand.leonardo.nutrimeal.cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.List;

import ardjomand.leonardo.nutrimeal.GlideApp;
import ardjomand.leonardo.nutrimeal.R;
import ardjomand.leonardo.nutrimeal.meals.MealsFragment.OnMealFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Cart} and makes a call to the
 * specified {@link OnMealFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CartMealAdapter extends RecyclerView.Adapter<CartMealAdapter.ViewHolder> {

    private final Context mContext;
    private final CartFragment.OnOrderedMealFragmentInteractionListener mListener;
    private List<CartMeal> mValues;

    CartMealAdapter(List<CartMeal> items, CartFragment.OnOrderedMealFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        String imagePath = mValues.get(position).getImagePath();
        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imagePath);

        if (mContext != null) {
            GlideApp.with(mContext)
                    .load(imageRef)
                    .apply(RequestOptions.centerCropTransform().fallback(R.mipmap.ic_launcher))
                    .into(holder.mImageView);
        }

        holder.mNameView.setText(mValues.get(position).getName());
        holder.mDescriptionView.setText(mValues.get(position).getDescription());

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String formattedUnitPrice = currencyFormat.format(mValues.get(position).getUnitPrice());
        String formattedTotalPrice = currencyFormat.format(mValues.get(position).getTotalPrice());
        if (mContext != null) {
            String cartMealText = mContext.getString(R.string.cart_meal_price,
                    holder.mItem.getQuantity(), formattedUnitPrice, formattedTotalPrice);
            holder.mPriceView.setText(cartMealText);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onOrderedMealClicked(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addData(CartMeal cartMeal) {
        mValues.add(cartMeal);
        notifyItemInserted(mValues.size() - 1);
    }

    public void clearData() {
        mValues.clear();
        notifyDataSetChanged();
    }

    public void updateData(CartMeal cartMeal) {
        int index = getIndexForKey(cartMeal.getKey());
        mValues.set(index, cartMeal);
        notifyItemChanged(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView mImageView;
        final TextView mNameView;
        final TextView mDescriptionView;
        final TextView mPriceView;
        CartMeal mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.meal_image);
            mNameView = view.findViewById(R.id.meal_name);
            mDescriptionView = view.findViewById(R.id.meal_description);
            mPriceView = view.findViewById(R.id.meal_price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }

    private int getIndexForKey(String key) {
        int index = 0;
        for (CartMeal cartMeal : mValues) {
            if (cartMeal.getKey().equals(key)) {
                return index;
            } else {
                index++;
            }
        }
        throw new IllegalArgumentException("Key not found");
    }
}
