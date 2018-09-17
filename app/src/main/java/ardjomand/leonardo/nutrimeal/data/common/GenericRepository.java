package ardjomand.leonardo.nutrimeal.data.common;

public interface GenericRepository {

    interface Repository {

        void subscribe();

        void unsubscribe();
    }

    interface Presenter<T> {

        void onItemAdded(T item);

        void onItemChanged(T item);

        void onItemRemoved(String key);
    }
}
