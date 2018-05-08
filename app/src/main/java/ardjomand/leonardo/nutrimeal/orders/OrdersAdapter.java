package ardjomand.leonardo.nutrimeal.orders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import ardjomand.leonardo.nutrimeal.R;
import ardjomand.leonardo.nutrimeal.meals.Meal;
import ardjomand.leonardo.nutrimeal.meals.MealsFragment.OnMealFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Meal} and makes a call to the
 * specified {@link OnMealFragmentInteractionListener}.
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private List<Order> mItems;
    private final OnMealAdapterInteractionListener mListener;

    public OrdersAdapter(List<Order> orders, OnMealAdapterInteractionListener listener) {
        mItems = orders;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mItems.get(position);
//        holder.mImageView.setText(mItems.get(position).getImagePath());
//        holder.mNameView.setText(mItems.get(position).getName());

//        holder.mDescriptionView.setText(mItems.get(position).getDescription());

        NumberFormat format = NumberFormat.getCurrencyInstance();
        holder.mPriceView.setText(format.format(mItems.get(position).getAmount()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onOrderClicked(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mImageView;
        final TextView mNameView;
        final TextView mDescriptionView;
        final TextView mPriceView;
        Order mItem;

        ViewHolder(View view) {
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

    public void replaceData(List<Order> orders) {
        mItems = orders;
        notifyDataSetChanged();
    }

    public void addData(Order order) {
        mItems.add(order);
        notifyItemInserted(mItems.size() - 1);
    }

    public interface OnMealAdapterInteractionListener {
        void onOrderClicked(Order item);
    }
}
