package ardjomand.leonardo.nutrimeal;

public interface ObserverView<T> {

    void addItem(T meal);

    void updateItem(T meal);

    void showEmptyItems();

    void showError();
}
