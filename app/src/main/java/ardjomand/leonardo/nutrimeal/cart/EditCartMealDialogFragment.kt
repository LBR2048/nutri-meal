package ardjomand.leonardo.nutrimeal.cart


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ardjomand.leonardo.nutrimeal.GlideApp
import ardjomand.leonardo.nutrimeal.R
import ardjomand.leonardo.nutrimeal.data.pojos.CartMeal
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_edit_cart_meal_dialog.*
import java.text.NumberFormat


class EditCartMealDialogFragment : DialogFragment(),
        View.OnClickListener,
        EditCartMealContract.View {

    private var mCartMealKey: String? = null
    private lateinit var presenter: EditCartMealContract.Presenter

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mCartMealKey = it.getString(ARG_CART_MEAL_KEY)
        }

        presenter = EditCartMealDialogPresenter(this, mCartMealKey)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_cart_meal_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe(mCartMealKey)
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }
    //endregion

    override fun updateCartMeal(cartMeal: CartMeal) {
        nameView.text = cartMeal.name
        quantityView.text = NumberFormat.getInstance().format(cartMeal.quantity.toLong())

        val imagePath = cartMeal.imagePath
        val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imagePath)

        context?.let {
            GlideApp.with(it)
                    .load(imageRef)
                    .apply(RequestOptions.centerCropTransform().fallback(R.mipmap.ic_launcher))
                    .into(imageView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.setView(null)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.edit_cart_meal_increase -> presenter.increaseQuantity()
            R.id.edit_cart_meal_decrease -> presenter.decreaseQuantity()
        }
    }

    companion object {

        private const val ARG_CART_MEAL_KEY = "arg_cart_meal_key"

        fun newInstance(key: String) =
                EditCartMealDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_CART_MEAL_KEY, key)
                    }
                }

        fun showDialog(activity: AppCompatActivity, key: String, tag: String) {
            newInstance(key).show(activity.supportFragmentManager, tag)
        }
    }
}
