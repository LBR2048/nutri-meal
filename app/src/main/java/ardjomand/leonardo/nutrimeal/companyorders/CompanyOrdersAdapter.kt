package ardjomand.leonardo.nutrimeal.companyorders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ardjomand.leonardo.nutrimeal.R
import ardjomand.leonardo.nutrimeal.Utils
import ardjomand.leonardo.nutrimeal.data.pojos.CompanyOrder

class CompanyOrdersAdapter
internal constructor(private val mItems: MutableList<CompanyOrder>,
                     private val mListener: OnMealAdapterInteractionListener?,
                     private val mContext: Context)
    : RecyclerView.Adapter<CompanyOrdersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_company_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mItem = mItems[position]

        holder.mIdView.text = Utils.formatOrderKey(mContext, mItem.key)

        holder.mCustomerView.text = mItem.customerKey

        val formattedDeliveryStatus = Utils.formatDeliveryStatus(mContext, mItem.isDelivered)
        holder.mDeliveryStatus.text = formattedDeliveryStatus

        val formattedAmount = Utils.formatAmount(mItem.amount)
        holder.mAmountView.text = formattedAmount

        val mealsMap = mItem.meals
        holder.mMealsView.text = Utils.formatMeals(mContext, mealsMap)

        holder.mView.setOnClickListener {
            mListener?.onOrderClicked(mItem)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun addData(companyOrder: CompanyOrder) {
        mItems.add(companyOrder)
        notifyItemInserted(mItems.size - 1)
    }

    fun clearData() {
        mItems.clear()
        notifyDataSetChanged()
    }

    fun updateData(companyOrder: CompanyOrder) {
        val index = Utils.getIndexForKey(companyOrder.key, mItems)
        mItems[index] = companyOrder
        notifyItemChanged(index)
    }

    interface OnMealAdapterInteractionListener {
        fun onOrderClicked(item: CompanyOrder)
    }

    inner class ViewHolder internal constructor(internal val mView: View) : RecyclerView.ViewHolder(mView) {
        internal val mIdView: TextView = mView.findViewById(R.id.order_id)
        internal val mCustomerView: TextView = mView.findViewById(R.id.order_customer)
        internal val mDeliveryStatus: TextView = mView.findViewById(R.id.order_delivery_status)
        internal val mAmountView: TextView = mView.findViewById(R.id.order_amount)
        internal val mMealsView: TextView = mView.findViewById(R.id.order_meals)

        override fun toString(): String {
            return super.toString() + " ' + ${mDeliveryStatus.text} + '"
        }
    }
}
