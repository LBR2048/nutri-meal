package ardjomand.leonardo.nutrimeal.customerorders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ardjomand.leonardo.nutrimeal.R
import ardjomand.leonardo.nutrimeal.Utils
import ardjomand.leonardo.nutrimeal.data.pojos.CustomerOrder

class CustomerOrdersAdapter
internal constructor(private val mItems: MutableList<CustomerOrder>,
                     private val mListener: OnMealAdapterInteractionListener?,
                     private val mContext: Context)
    : RecyclerView.Adapter<CustomerOrdersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_customer_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mItem = mItems[position]

        holder.mIdView.text = mContext.getString(R.string.order_id, mItem.key)

        val formattedDeliveryStatus = Utils.formatDeliveryStatus(mContext, mItems[position].isDelivered)
        holder.mDeliveryStatus.text = formattedDeliveryStatus

        val formattedAmount = mItems[position].amount
        holder.mAmountView.text = Utils.formatAmount(formattedAmount)

        holder.mView.setOnClickListener {
            mListener?.onOrderClicked(mItem)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun addData(customerOrder: CustomerOrder) {
        mItems.add(customerOrder)
        notifyItemInserted(mItems.size - 1)
    }

    fun clearData() {
        mItems.clear()
        notifyDataSetChanged()
    }

    fun updateData(customerOrder: CustomerOrder) {
        val index = Utils.getIndexForKey(customerOrder.key, mItems)
        mItems[index] = customerOrder
        notifyItemChanged(index)
    }

    interface OnMealAdapterInteractionListener {
        fun onOrderClicked(item: CustomerOrder)
    }

    inner class ViewHolder internal constructor(internal val mView: View) : RecyclerView.ViewHolder(mView) {
        internal val mIdView: TextView = mView.findViewById(R.id.order_id)
        internal val mDeliveryStatus: TextView = mView.findViewById(R.id.order_delivery_status)
        internal val mAmountView: TextView = mView.findViewById(R.id.order_amount)

        override fun toString(): String {
            return super.toString() + " ' + ${mDeliveryStatus.text} + '"
        }
    }
}
