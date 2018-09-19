package ardjomand.leonardo.nutrimeal.data.common

interface GenericItemRepository {

    interface Repository {

        fun subscribe(key: String)

        fun unsubscribe()
    }

    interface Presenter<T> {

        fun onItemRead(item: T)
    }
}
