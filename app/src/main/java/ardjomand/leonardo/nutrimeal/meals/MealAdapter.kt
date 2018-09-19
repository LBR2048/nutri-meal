package ardjomand.leonardo.nutrimeal.meals

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ardjomand.leonardo.nutrimeal.R
import ardjomand.leonardo.nutrimeal.Utils
import ardjomand.leonardo.nutrimeal.data.pojos.Meal
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_meal.view.*
import java.text.NumberFormat

class MealAdapter(private val mMeals: MutableList<Meal>,
                  private val mListener: OnMealAdapterInteractionListener?,
                  private val mContext: Context)
    : RecyclerView.Adapter<MealAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_meal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mItem = mMeals[position]

        val imagePath = mMeals[position].imagePath
        if (imagePath != null && !imagePath.isEmpty()) {
            FirebaseStorage.getInstance().getReferenceFromUrl(imagePath).downloadUrl
                    .addOnSuccessListener { uri ->
                        if (uri != null) {
                            Glide.with(mContext)
                                    .load(uri)
                                    .apply(RequestOptions.fitCenterTransform().fallback(R.mipmap.ic_launcher))
                                    .into(holder.mImageView)
                        }
                    }
                    .addOnFailureListener { exception -> Log.e(TAG, exception.message) }
        }

        holder.mNameView.text = mMeals[position].name
        holder.mDescriptionView.text = mMeals[position].description

        val format = NumberFormat.getCurrencyInstance()
        holder.mPriceView.text = format.format(mMeals[position].unitPrice)

        holder.mView.setOnClickListener {
            mListener?.onMealClicked(mItem)
        }
    }

    override fun getItemCount(): Int {
        return mMeals.size
    }

    fun updateData(meal: Meal) {
        val index = Utils.getIndexForKey(meal.key, mMeals)
        mMeals[index] = meal
        notifyItemChanged(index)
    }

    inner class ViewHolder internal constructor(internal val mView: View) : RecyclerView.ViewHolder(mView) {
        internal val mImageView: ImageView = mView.meal_image
        internal val mNameView: TextView = mView.meal_name
        internal val mDescriptionView: TextView = mView.meal_description
        internal val mPriceView: TextView = mView.meal_price

        override fun toString(): String {
            return super.toString() + " ' + ${mNameView.text} + '"
        }
    }

    fun addData(meal: Meal) {
        mMeals.add(meal)
        notifyItemInserted(mMeals.size - 1)
    }

    fun clearData() {
        mMeals.clear()
        notifyDataSetChanged()
    }

    interface OnMealAdapterInteractionListener {
        fun onMealClicked(item: Meal)
    }

    companion object {

        private val TAG = MealAdapter::class.java.simpleName
    }

}
