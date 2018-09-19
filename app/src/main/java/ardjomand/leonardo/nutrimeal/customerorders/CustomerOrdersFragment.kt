package ardjomand.leonardo.nutrimeal.customerorders

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
import ardjomand.leonardo.nutrimeal.data.pojos.CustomerOrder
import ardjomand.leonardo.nutrimeal.widget.OrdersWidget
import kotlinx.android.synthetic.main.fragment_orders.*
import java.util.*

//endregion

//region Constructors
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class CustomerOrdersFragment : Fragment(), CustomerOrdersContract.View<CustomerOrder>, CustomerOrdersAdapter.OnMealAdapterInteractionListener {

    //region Member variables
    private var columnCount = 1
    private lateinit var presenter: CustomerOrdersContract.Presenter
    private lateinit var adapter: CustomerOrdersAdapter
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        presenter = CustomerOrdersPresenter(this)
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
        adapter = CustomerOrdersAdapter(ArrayList(), this, getContext())
        list.adapter = adapter

        // Set decoration
        val itemDecoration = DividerItemDecoration(list.context, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(itemDecoration)
    }

    override fun onStart() {
        super.onStart()
        adapter.clearData()
        presenter.subscribe()
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.setView(null)
    }

    //endregion

    //region Presenter callbacks
    override fun addItem(customerOrder: CustomerOrder) {
        adapter.addData(customerOrder)
        OrdersWidget.updateAllWidgets(context)
    }

    override fun updateItem(customerOrder: CustomerOrder) {
        adapter.updateData(customerOrder)
        OrdersWidget.updateAllWidgets(context)
    }

    override fun showEmptyItems() {
        Toast.makeText(activity, R.string.error_no_items_available, Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        Toast.makeText(activity, R.string.error_items_could_not_be_downloaded, Toast.LENGTH_SHORT).show()
    }
    //endregion

    // TODO move the responsibility of setting title to the parent activity
    // this will make it easier for the fragments to be added instead of replaced
    private fun setTitle() {
        if (activity is AppCompatActivity) {
            val supportActionBar = (activity as AppCompatActivity).supportActionBar
            supportActionBar?.apply{
                setTitle(R.string.orders_title)
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    override fun onOrderClicked(customerOrder: CustomerOrder) {
        // TODO Add possibility to see more details about the order
    }

    companion object {

        private const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
                CustomerOrdersFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
