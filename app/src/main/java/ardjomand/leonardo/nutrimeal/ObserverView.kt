package ardjomand.leonardo.nutrimeal

interface ObserverView<T> {

    fun addItem(meal: T)

    fun updateItem(meal: T)

    fun showEmptyItems()

    fun showError()
}
