package ardjomand.leonardo.nutrimeal.orders;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.OrdersRepository;
import ardjomand.leonardo.nutrimeal.data.OrdersRepositoryImpl;

public class OrdersPresenter implements OrdersContract.Presenter, OrdersRepository.Presenter {

    public static final String TAG = OrdersPresenter.class.getSimpleName();
    private final OrdersRepository.Repository repository;
    private OrdersContract.View view;

    public OrdersPresenter(OrdersContract.View view) {
        this.view = view;
        repository = new OrdersRepositoryImpl(this);
    }

    @Override
    public void subscribeToOrdersUpdates() {
        repository.subscribeForOrdersUpdates();
    }

    @Override
    public void unsubscribeFromOrdersUpdates() {
        repository.unsubscribeFromOrdersUpdates();
    }

    @Override
    public void onOrderAdded(Order order) {
        Log.i(TAG, "Order " + order.toString() + " added");
        view.addOrder(order);
    }

    @Override
    public void onOrderChanged(Order order) {
        view.updateOrder(order);
    }

    @Override
    public void onOrderRemoved(String key) {

    }
}
