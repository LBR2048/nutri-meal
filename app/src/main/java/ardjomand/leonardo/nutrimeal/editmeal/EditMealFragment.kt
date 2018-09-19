package ardjomand.leonardo.nutrimeal.editmeal


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.Toast
import ardjomand.leonardo.nutrimeal.GlideApp
import ardjomand.leonardo.nutrimeal.R
import ardjomand.leonardo.nutrimeal.data.pojos.Meal
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_edit_meal.*

class EditMealFragment : Fragment(),
        View.OnClickListener,
        View.OnFocusChangeListener,
        EditMealContract.View<Meal> {

    private lateinit var presenter: EditMealContract.Presenter
    private var key: String? = null

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        arguments?.let {
            key = it.getString(ARG_KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_edit_meal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()

        presenter = EditMealPresenter(this, key)
    }

    override fun onStart() {
        super.onStart()

        presenter.subscribe(key)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(STATE_KEY, key)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            key = savedInstanceState.getString(STATE_KEY)
        }

        editMealImage.setOnClickListener{
            pickPhotoFromGallery()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.setView(null)
    }
    //endregion

    //region Menu
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_edit_meal_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit_meal_delete -> {
                presenter.deleteMeal(key)
                fragmentManager?.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //endregion

    //region Presenter callbacks
    override fun showItem(meal: Meal) {
        key = meal.key
        editMealName.setText(meal.name)
        editMealDescription.setText(meal.description)

        // TODO add $ as a label
        //        NumberFormat format = NumberFormat.getCurrencyInstance();
        //        editMealPrice.setText(format.format(meal.getUnitPrice()));
        editMealPrice.setText(meal.unitPrice.toString())
    }

    override fun showMealImage(imagePath: String) {
        val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imagePath)

        context?.let {
            GlideApp.with(it)
                    .load(imageRef)
                    .apply(RequestOptions.centerCropTransform().fallback(R.mipmap.ic_launcher))
                    .into(editMealImage)
        }
    }

    override fun showAddMealImageIcon() {
        editMealImage.setImageResource(R.drawable.ic_add_a_photo_black_24dp)
    }

    override fun showError() {
        Toast.makeText(activity, R.string.error_items_could_not_be_downloaded, Toast.LENGTH_SHORT).show()
    }
    //endregion

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE_CODE) {
                val imageUri = data.data
                presenter.updateMealImage(key, imageUri)
            }
        }
    }

    //region Helper methods
    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (activity != null) {
            if (intent.resolveActivity(activity?.packageManager) != null) {
                startActivityForResult(intent, PICK_IMAGE_CODE)
            }
        }
    }

    // TODO move the responsibility of setting title to the parent activity
    // this will make it easier for the fragments to be added instead of replaced
    private fun setTitle() {
        if (activity is AppCompatActivity) {
            val supportActionBar = (activity as AppCompatActivity).supportActionBar
            supportActionBar?.apply {
                // TODO change title to Add new meal if the FAB is clicked
                setTitle(R.string.edit_meal_title)
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    private fun updateMeal() {
        val name = editMealName.text.toString()
        val description = editMealDescription.text.toString()
        val unitPriceString = editMealPrice.text.toString()

        val unitPrice: Long
        unitPrice = if (!unitPriceString.isEmpty()) {
            java.lang.Long.parseLong(unitPriceString)
        } else {
            0
        }

        val updatedMeal = Meal(key, name, description, "", unitPrice, true)
        presenter.updateMeal(updatedMeal)
    }
    //endregion

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.editMealImage -> pickPhotoFromGallery()
        }
    }

    override fun onFocusChange(view: View?, focused: Boolean) {
        when(view?.id) {
            R.id.editMealName, R.id.editMealDescription, R.id.editMealPrice -> if (!focused) updateMeal()
        }
    }

    companion object {

        private const val STATE_KEY = "state-key"
        private const val ARG_KEY = "arg-meal"
        private const val PICK_IMAGE_CODE = 1046

        fun newInstance(param1: String) =
                EditMealFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_KEY, param1)
                    }
                }
    }
}// Required empty public constructor
