package ardjomand.leonardo.nutrimeal.companyorders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ardjomand.leonardo.nutrimeal.R;
import ardjomand.leonardo.nutrimeal.Utils;
import ardjomand.leonardo.nutrimeal.data.pojos.CartMeal;
import ardjomand.leonardo.nutrimeal.data.pojos.CompanyOrder;
import ardjomand.leonardo.nutrimeal.data.pojos.Meal;
import ardjomand.leonardo.nutrimeal.meals.MealsFragment.OnMealFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Meal} and makes a call to the
 * specified {@link OnMealFragmentInteractionListener}.
 */
public class CompanyOrdersAdapter extends RecyclerView.Adapter<CompanyOrdersAdapter.ViewHolder> {

    private final OnMealAdapterInteractionListener mListener;
    private final Context mContext;
    private List<CompanyOrder> mItems;

    CompanyOrdersAdapter(List<CompanyOrder> companyOrders, OnMealAdapterInteractionListener listener, Context context) {
        mContext = context;
        mItems = companyOrders;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_company_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mItems.get(position);

        holder.mIdView.setText(Utils.formatOrderKey(mContext, holder.mItem.getKey()));

        holder.mCustomerView.setText(holder.mItem.getCustomerKey());

        String formattedDeliveryStatus = Utils.formatDeliveryStatus(mContext, holder.mItem.isDelivered());
        holder.mDeliveryStatus.setText(formattedDeliveryStatus);

        String formattedAmount = Utils.formatAmount(holder.mItem.getAmount());
        holder.mAmountView.setText(formattedAmount);

        Map<String, CartMeal> mealsMap = holder.mItem.getMeals();
        holder.mMealsView.setText(Utils.formatMeals(mContext, mealsMap));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
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

    public void addData(CompanyOrder companyOrder) {
        mItems.add(companyOrder);
        notifyItemInserted(mItems.size() - 1);
    }

    public void clearData() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void updateData(CompanyOrder companyOrder) {
        int index = getIndexForKey(companyOrder.getKey());
        mItems.set(index, companyOrder);
        notifyItemChanged(index);
    }

    private int getIndexForKey(String key) {
        int index = 0;
        for (CompanyOrder companyOrder : mItems) {
            if (companyOrder.getKey().equals(key)) {
                return index;
            } else {
                index++;
            }
        }
        throw new IllegalArgumentException("Key not found");
    }

    public interface OnMealAdapterInteractionListener {
        void onOrderClicked(CompanyOrder item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mIdView;
        final TextView mCustomerView;
        final TextView mDeliveryStatus;
        final TextView mAmountView;
        final TextView mMealsView;
        CompanyOrder mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.order_id);
            mCustomerView = view.findViewById(R.id.order_customer);
            mDeliveryStatus = view.findViewById(R.id.order_delivery_status);
            mAmountView = view.findViewById(R.id.order_amount);
            mMealsView = view.findViewById(R.id.order_meals);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDeliveryStatus.getText() + "'";
        }
    }
}
