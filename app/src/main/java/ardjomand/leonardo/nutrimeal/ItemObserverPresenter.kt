package ardjomand.leonardo.nutrimeal

interface ItemObserverPresenter {

    fun subscribe(key: String)

    fun unsubscribe()
}
