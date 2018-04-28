package ardjomand.leonardo.nutrimeal.meals;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ardjomand.leonardo.nutrimeal.R;
import ardjomand.leonardo.nutrimeal.meals.MealFragment.OnMealFragmentInteractionListener;

import java.text.NumberFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Meal} and makes a call to the
 * specified {@link OnMealFragmentInteractionListener}.
 */
public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    private List<Meal> mMeals;
    private final OnMealAdapterInteractionListener mListener;

    public MealAdapter(List<Meal> meals, OnMealAdapterInteractionListener listener) {
        mMeals = meals;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mMeals.get(position);
        holder.mImageView.setText(mMeals.get(position).getImagePath());
        holder.mNameView.setText(mMeals.get(position).getName());
        holder.mDescriptionView.setText(mMeals.get(position).getDescription());

        NumberFormat format = NumberFormat.getCurrencyInstance();
        holder.mPriceView.setText(format.format(mMeals.get(position).getUnitPrice()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onMealClicked(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mImageView;
        final TextView mNameView;
        final TextView mDescriptionView;
        final TextView mPriceView;
        Meal mItem;

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

    public void replaceData(List<Meal> meals) {
        mMeals = meals;
        notifyDataSetChanged();
    }

    public void addData(Meal meal) {
        mMeals.add(meal);
        notifyItemInserted(mMeals.size() - 1);
    }

    public interface OnMealAdapterInteractionListener {
        void onMealClicked(Meal item);
    }
}
