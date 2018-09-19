package ardjomand.leonardo.nutrimeal.users

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ardjomand.leonardo.nutrimeal.R
import ardjomand.leonardo.nutrimeal.data.pojos.User
import kotlinx.android.synthetic.main.fragment_orders.*
import java.util.*

//endregion

//region Constructors
class UsersFragment : Fragment(),
        UsersContract.View,
        UsersAdapter.OnMealAdapterInteractionListener {

    //region Member variables
    private var columnCount = 1
    private lateinit var presenter: UsersContract.Presenter
    private lateinit var adapter: UsersAdapter
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        presenter = UsersPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()

        // Set layout manager
        val context = view.context
        list.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }

        // Set adapter
        adapter = UsersAdapter(ArrayList(), this, getContext())
        list.adapter = adapter

        // Set decoration
        val itemDecoration = DividerItemDecoration(list.context, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(itemDecoration)

    }

    override fun onStart() {
        super.onStart()
        adapter.clearData()
        presenter.subscribeToUsersUpdates()
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribeFromUsersUpdates()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.setView(null)
    }

    //endregion

    //region Presenter callbacks
    override fun addUser(user: User) {
        adapter.addData(user)
    }

    override fun updateUser(user: User) {
        adapter.updateData(user)
    }

    override fun showEmptyUser() {
        Toast.makeText(activity, R.string.error_no_items_available, Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        Toast.makeText(activity, R.string.error_items_could_not_be_downloaded, Toast.LENGTH_SHORT).show()
    }
    //endregion

    private fun setTitle() {
        if (activity is AppCompatActivity) {
            val supportActionBar = (activity as AppCompatActivity).supportActionBar
            supportActionBar?.setTitle(R.string.orders_title)
        }
    }

    override fun onUserClicked(item: User) {
        Toast.makeText(context, item.name + " clicked", Toast.LENGTH_SHORT).show()
    }

    companion object {

        private const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
                UsersFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
