package ardjomand.leonardo.nutrimeal.data.common;

public interface GenericItemRepository {

    interface Repository {

        void subscribe(String key);

        void unsubscribe();
    }

    interface Presenter<T> {

        void onItemRead(T item);
    }
}
