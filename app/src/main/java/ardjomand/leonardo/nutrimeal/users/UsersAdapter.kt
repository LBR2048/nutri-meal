package ardjomand.leonardo.nutrimeal.users

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ardjomand.leonardo.nutrimeal.R
import ardjomand.leonardo.nutrimeal.Utils
import ardjomand.leonardo.nutrimeal.data.pojos.User

class UsersAdapter internal constructor(private var mItems: MutableList<User>?, private val mListener: OnMealAdapterInteractionListener?, context: Context) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mItem = mItems!![position]

        holder.mNameView.text = mItem.name

        holder.mView.setOnClickListener {
            mListener?.onUserClicked(mItem)
        }
    }

    override fun getItemCount(): Int {
        return mItems!!.size
    }

    fun addData(user: User) {
        mItems!!.add(user)
        notifyItemInserted(mItems!!.size - 1)
    }

    fun clearData() {
        mItems!!.clear()
        notifyDataSetChanged()
    }

    fun updateData(user: User) {
        val index = Utils.getIndexForKey(user.key, mItems!!)
        mItems!![index] = user
        notifyItemChanged(index)
    }

    interface OnMealAdapterInteractionListener {
        fun onUserClicked(item: User)
    }

    inner class ViewHolder internal constructor(internal val mView: View) : RecyclerView.ViewHolder(mView) {
        internal val mNameView: TextView = mView.findViewById(R.id.user_name)

        override fun toString(): String {
            return super.toString() + " ' + ${mNameView.text} + '"
        }
    }
}
