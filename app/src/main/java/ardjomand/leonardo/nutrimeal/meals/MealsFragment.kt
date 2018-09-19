package ardjomand.leonardo.nutrimeal.meals

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import ardjomand.leonardo.nutrimeal.BuildConfig
import ardjomand.leonardo.nutrimeal.R
import ardjomand.leonardo.nutrimeal.data.pojos.Meal
import kotlinx.android.synthetic.company.fragment_meals.*
import java.util.*

class MealsFragment : Fragment(),
        View.OnClickListener,
        MealsContract.View<Meal>,
        MealAdapter.OnMealAdapterInteractionListener {

    //region Member variables
    private var columnCount = 1
    private var listener: OnMealFragmentInteractionListener? = null
    private lateinit var presenter: MealsContract.Presenter
    private lateinit var adapter: MealAdapter
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        presenter = MealsPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()

        val context = view.context

        // Set layout manager
        list.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }

        // Set adapter
        adapter = MealAdapter(ArrayList(), this, context)
        list.adapter = adapter

        // Set decoration
        val itemDecoration = DividerItemDecoration(list.context, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(itemDecoration)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMealFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnOrdersFragmentInteractionListener")
        }
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

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.setView(null)
    }
    //endregion

    //region Menu
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_meals_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_orders -> listener?.onGoToOrdersClicked()
        }
        return super.onOptionsItemSelected(item)
    }
    //endregion

    //region Presenter callbacks
    override fun addItem(meal: Meal) {
        adapter.addData(meal)
    }

    override fun updateItem(meal: Meal) {
        adapter.updateData(meal)
    }

    override fun showEmptyItems() {
        Toast.makeText(activity, R.string.error_no_items_available, Toast.LENGTH_SHORT).show()
    }

    override fun showError() {
        Toast.makeText(activity, R.string.error_items_could_not_be_downloaded, Toast.LENGTH_SHORT).show()
    }

    override fun goToEditMeal(key: String) {
        listener?.onEditMealClicked(key)
    }

    override fun goToEditCartMealQuantity(key: String) {
        listener?.onEditCartMealQuantity(key)
    }
    //endregion

    // TODO move the responsibility of setting title to the parent activity
    // this will make it easier for the fragments to be added instead of replaced
    private fun setTitle() {
        if (activity is AppCompatActivity) {
            val supportActionBar = (activity as AppCompatActivity).supportActionBar
            supportActionBar?.apply {
                setTitle(R.string.meals_title)
                setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button -> listener?.onGoToCartClicked()

            R.id.meals_add_fab -> presenter.createNewMeal()
        }
    }

    override fun onMealClicked(meal: Meal) {
        if (BuildConfig.FLAVOR == "company") {
            presenter.editMeal(meal.key)
        } else {
            // TODO add meal to cart only if item quantity in cart is zero
            presenter.addMealToCart(meal)
            presenter.editCartMealQuantity(meal.key)
            // TODO only show toast if meal was successfully added to cart
            //            Toast.makeText(getContext(), meal.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        }
    }

    interface OnMealFragmentInteractionListener {
        fun onEditMealClicked(key: String)
        fun onEditCartMealQuantity(key: String)
        fun onGoToCartClicked()
        fun onGoToOrdersClicked()
    }

    companion object {

        private const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
                MealsFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
