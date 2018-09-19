package ardjomand.leonardo.nutrimeal

interface ObserverView<T> {

    fun addItem(item: T)

    fun updateItem(item: T)

    fun showEmptyItems()

    fun showError()
}
