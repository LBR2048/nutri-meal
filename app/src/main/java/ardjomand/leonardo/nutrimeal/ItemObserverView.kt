package ardjomand.leonardo.nutrimeal

interface ItemObserverView<T> {

    fun showItem(item: T)

    fun showError()
}
