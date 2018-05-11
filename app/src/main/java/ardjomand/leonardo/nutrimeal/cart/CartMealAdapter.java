package ardjomand.leonardo.nutrimeal.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

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

    public CartMealAdapter(List<CartMeal> items, CartFragment.OnOrderedMealFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mImageView.setText(mValues.get(position).getImagePath());
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mDescriptionView.setText(mValues.get(position).getDescription());

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String formattedUnitPRice = currencyFormat.format(mValues.get(position).getUnitPrice());
        String formattedTotalPrice = currencyFormat.format(mValues.get(position).getTotalPrice());
        holder.mPriceView.setText(mContext.getString(R.string.cart_meal_price,
                holder.mItem.getQuantity(), formattedUnitPRice, formattedTotalPrice));

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

    public void replaceData(List<CartMeal> cartMeals) {
        mValues = cartMeals;
        notifyDataSetChanged();
    }

    public void addData(CartMeal cartMeal) {
        mValues.add(cartMeal);
        notifyItemInserted(mValues.size() - 1);
    }

    public void clearData() {
        mValues.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mImageView;
        public final TextView mNameView;
        public final TextView mDescriptionView;
        public final TextView mPriceView;
        public CartMeal mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.meal_imagePath);
            mNameView = view.findViewById(R.id.meal_name);
            mDescriptionView = view.findViewById(R.id.meal_description);
            mPriceView = view.findViewById(R.id.meal_price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mImageView.getText() + "'";
        }
    }
}
