package ardjomand.leonardo.nutrimeal.orders;

import android.content.Context;
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

    private final OnMealAdapterInteractionListener mListener;
    private final Context mContext;
    private List<Order> mItems;

    public OrdersAdapter(List<Order> orders, OnMealAdapterInteractionListener listener, Context context) {
        mContext = context;
        mItems = orders;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mItems.get(position);

        // TODO show correct ID
        holder.mIdView.setText("Order ID");

        holder.mDeliveryStatus.setText(mItems.get(position).isDelivered()
                ? mContext.getString(R.string.order_delivered)
                : mContext.getString(R.string.order_not_delivered));

        NumberFormat format = NumberFormat.getCurrencyInstance();
        holder.mAmountView.setText(format.format(mItems.get(position).getAmount()));

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

    public void replaceData(List<Order> orders) {
        mItems = orders;
        notifyDataSetChanged();
    }

    public void addData(Order order) {
        mItems.add(order);
        notifyItemInserted(mItems.size() - 1);
    }

    public void clearData() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public interface OnMealAdapterInteractionListener {
        void onOrderClicked(Order item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mIdView;
        final TextView mDeliveryStatus;
        final TextView mAmountView;
        Order mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.order_id);
            mDeliveryStatus = view.findViewById(R.id.order_delivery_status);
            mAmountView = view.findViewById(R.id.order_amount);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDeliveryStatus.getText() + "'";
        }
    }
}
