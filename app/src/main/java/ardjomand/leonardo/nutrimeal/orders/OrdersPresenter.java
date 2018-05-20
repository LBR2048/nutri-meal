package ardjomand.leonardo.nutrimeal.orders;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.customerorders.Order;
import ardjomand.leonardo.nutrimeal.data.CustomerOrdersRepository;
import ardjomand.leonardo.nutrimeal.data.OrdersRepositoryImpl;

public class OrdersPresenter implements OrdersContract.Presenter, CustomerOrdersRepository.Presenter {

    public static final String TAG = OrdersPresenter.class.getSimpleName();
    private final CustomerOrdersRepository.Repository repository;
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
