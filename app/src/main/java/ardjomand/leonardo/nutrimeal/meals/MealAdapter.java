package ardjomand.leonardo.nutrimeal.meals;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.NumberFormat;
import java.util.List;

import ardjomand.leonardo.nutrimeal.R;
import ardjomand.leonardo.nutrimeal.meals.MealsFragment.OnMealFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Meal} and makes a call to the
 * specified {@link OnMealFragmentInteractionListener}.
 */
public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    public static final String TAG = MealAdapter.class.getSimpleName();
    private final Context mContext;
    private List<Meal> mMeals;
    private final OnMealAdapterInteractionListener mListener;

    public MealAdapter(List<Meal> meals, OnMealAdapterInteractionListener listener, Context context) {
        mContext = context;
        mMeals = meals;
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
        holder.mItem = mMeals.get(position);

        String imagePath = mMeals.get(position).getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            FirebaseStorage.getInstance().getReferenceFromUrl(imagePath).getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri != null) {
                                Glide.with(mContext)
                                        .load(uri)
                                        .apply(RequestOptions.fitCenterTransform().fallback(R.mipmap.ic_launcher))
                                        .into(holder.mImageView);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.e(TAG, exception.getMessage());
                        }
                    });
        }

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

    public void updateData(Meal meal) {
        int index = getIndexForKey(meal.getKey());
        mMeals.set(index, meal);
        notifyItemChanged(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView mImageView;
        final TextView mNameView;
        final TextView mDescriptionView;
        final TextView mPriceView;
        Meal mItem;

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

    public void replaceData(List<Meal> meals) {
        mMeals = meals;
        notifyDataSetChanged();
    }

    public void addData(Meal meal) {
        mMeals.add(meal);
        notifyItemInserted(mMeals.size() - 1);
    }

    public void clearData() {
        mMeals.clear();
        notifyDataSetChanged();
    }

    public interface OnMealAdapterInteractionListener {
        void onMealClicked(Meal item);
    }

    // TODO move to Utils class using generic type
    private int getIndexForKey(String key) {
        int index = 0;
        for (Meal meal : mMeals) {
            if (meal.getKey().equals(key)) {
                return index;
            } else {
                index++;
            }
        }
        throw new IllegalArgumentException("Key not found");
    }
}
