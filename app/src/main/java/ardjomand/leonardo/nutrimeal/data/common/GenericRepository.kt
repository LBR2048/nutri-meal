package ardjomand.leonardo.nutrimeal.data.common

interface GenericRepository {

    interface Repository {

        fun subscribe()

        fun unsubscribe()
    }

    interface Presenter<T> {

        fun onItemAdded(item: T)

        fun onItemChanged(item: T)

        fun onItemRemoved(key: String)
    }
}
