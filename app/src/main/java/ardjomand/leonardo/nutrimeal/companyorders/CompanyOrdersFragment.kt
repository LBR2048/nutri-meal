package ardjomand.leonardo.nutrimeal.companyorders

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
import ardjomand.leonardo.nutrimeal.data.pojos.CompanyOrder
import ardjomand.leonardo.nutrimeal.widget.OrdersWidget
import kotlinx.android.synthetic.main.fragment_orders.*
import java.util.*

class CompanyOrdersFragment : Fragment(),
        CompanyOrdersContract.View<CompanyOrder>,
        CompanyOrdersAdapter.OnMealAdapterInteractionListener {

    //region Member variables
    private var columnCount = 1
    private lateinit var presenter: CompanyOrdersContract.Presenter
    private lateinit var adapter: CompanyOrdersAdapter
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        presenter = CompanyOrdersPresenter(this)
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
        adapter = CompanyOrdersAdapter(ArrayList(), this, getContext())
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
    override fun addItem(companyOrder: CompanyOrder) {
        adapter.addData(companyOrder)
        OrdersWidget.updateAllWidgets(context)
    }

    override fun updateItem(companyOrder: CompanyOrder) {
        adapter.updateData(companyOrder)
        OrdersWidget.updateAllWidgets(context)
    }

    override fun showEmptyItems() {
        Toast.makeText(activity, R.string.error_items_could_not_be_downloaded, Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        Toast.makeText(activity, R.string.error_no_items_available, Toast.LENGTH_SHORT).show()
    }
    //endregion

    // TODO move the responsibility of setting title to the parent activity
    // this will make it easier for the fragments to be added instead of replaced
    private fun setTitle() {
        if (activity is AppCompatActivity) {
            val supportActionBar = (activity as AppCompatActivity).supportActionBar
            supportActionBar?.apply {
                setTitle(R.string.orders_title)
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    override fun onOrderClicked(companyOrder: CompanyOrder) {
        // TODO Add possibility to change order status
    }

    companion object {

        private const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
                CompanyOrdersFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
