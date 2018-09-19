package ardjomand.leonardo.nutrimeal.cart

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ardjomand.leonardo.nutrimeal.GlideApp
import ardjomand.leonardo.nutrimeal.R
import ardjomand.leonardo.nutrimeal.Utils
import ardjomand.leonardo.nutrimeal.data.pojos.CartMeal
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_cart.view.*
import java.text.NumberFormat

class CartMealAdapter
internal constructor(private val mValues: MutableList<CartMeal>,
                     private val mListener: CartFragment.OnOrderedMealFragmentInteractionListener?,
                     private val mContext: Context)
    : RecyclerView.Adapter<CartMealAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mItem = mValues[position]

        val imagePath = mValues[position].imagePath
        val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imagePath)

        GlideApp.with(mContext)
                .load(imageRef)
                .apply(RequestOptions.centerCropTransform().fallback(R.mipmap.ic_launcher))
                .into(holder.mImageView)

        holder.mNameView.text = mValues[position].name
        holder.mDescriptionView.text = mValues[position].description

        val currencyFormat = NumberFormat.getCurrencyInstance()
        val formattedUnitPrice = currencyFormat.format(mValues[position].unitPrice)
        val formattedTotalPrice = currencyFormat.format(mValues[position].totalPrice)
        val cartMealText = mContext.getString(R.string.cart_meal_price,
                mItem.quantity, formattedUnitPrice, formattedTotalPrice)
        holder.mPriceView.text = cartMealText

        holder.mView.setOnClickListener {
            mListener?.onOrderedMealClicked(mItem)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    fun addData(cartMeal: CartMeal) {
        mValues.add(cartMeal)
        notifyItemInserted(mValues.size - 1)
    }

    fun clearData() {
        mValues.clear()
        notifyDataSetChanged()
    }

    fun updateData(cartMeal: CartMeal) {
        val index = Utils.getIndexForKey(cartMeal.key, mValues)
        mValues[index] = cartMeal
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
}
