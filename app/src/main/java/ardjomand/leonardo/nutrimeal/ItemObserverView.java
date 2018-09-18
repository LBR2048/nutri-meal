package ardjomand.leonardo.nutrimeal;

public interface ItemObserverView<T> {

    void showItem(T item);

    void showError();
}
