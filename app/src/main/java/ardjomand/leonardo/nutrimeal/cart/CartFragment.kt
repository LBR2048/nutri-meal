package ardjomand.leonardo.nutrimeal.cart

import android.content.Context
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
import ardjomand.leonardo.nutrimeal.data.pojos.CartMeal
import kotlinx.android.synthetic.main.fragment_cart.*
import java.util.*

class CartFragment : Fragment(),
        View.OnClickListener,
        CartContract.View {

    //region Member variables
    private var columnCount = 1
    private var listener: OnOrderedMealFragmentInteractionListener? = null
    private lateinit var presenter: CartContract.Presenter
    private lateinit var adapter: CartMealAdapter
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        presenter = CartPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()

        // Set the layout manager
        val context = view.context
        list.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }

        // Set the adapter
        adapter = CartMealAdapter(ArrayList(), listener, getContext())
        list.adapter = adapter

        val itemDecoration = DividerItemDecoration(list.context, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(itemDecoration)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnOrderedMealFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnOrdersFragmentInteractionListener")
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.clearData()
        presenter.subscribeToCartUpdates()
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribeFromCartUpdates()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.setView(null)
    }
    //endregion

    //region Presenter callbacks
    override fun addCartMeal(cartMeal: CartMeal) {
        adapter.addData(cartMeal)
    }

    override fun updateCartMeal(cartMeal: CartMeal) {
        adapter.updateData(cartMeal)
    }

    override fun showEmptyMeals() {
        Toast.makeText(activity, R.string.error_no_items_available, Toast.LENGTH_SHORT).show()
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
                setTitle(R.string.cart_title)
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button -> {
                presenter.placeOrder()
                // TODO only call next line if order was placed successfully
                listener?.onPlaceOrderClicked()
            }
        }
    }

    interface OnOrderedMealFragmentInteractionListener {
        fun onOrderedMealClicked(item: CartMeal)
        fun onPlaceOrderClicked()
    }

    companion object {

        private const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
                CartFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
