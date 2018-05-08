package ardjomand.leonardo.nutrimeal.orders;

import android.util.Log;

public class OrdersPresenter implements OrdersContract.Presenter, OrdersRepository.Presenter {

    public static final String TAG = OrdersPresenter.class.getSimpleName();

    private OrdersContract.View view;
    private final OrdersRepository.Repository repository;

    public OrdersPresenter(OrdersContract.View view) {
        this.view = view;
        repository = new OrdersRepositoryImpl(this);
    }

    @Override
    public void getOrders() {
        Log.i(TAG, "Getting orders");
        repository.subscribeForOrdersUpdates();
    }

    @Override
    public void onOrderAdded(Order order) {
        Log.i(TAG, "Order " + order.toString() + " added");
        view.addOrder(order);
    }

    @Override
    public void onOrderChanged(Order order) {

    }

    @Override
    public void onOrderRemoved(String key) {

    }
}
