package ardjomand.leonardo.nutrimeal.customerorders;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.CustomerOrdersRepository;
import ardjomand.leonardo.nutrimeal.data.CustomerOrdersRepositoryImpl;

public class CustomerOrdersPresenter implements CustomerOrdersContract.Presenter, CustomerOrdersRepository.Presenter {

    public static final String TAG = CustomerOrdersPresenter.class.getSimpleName();
    private final CustomerOrdersRepository.Repository repository;
    private CustomerOrdersContract.View view;

    public CustomerOrdersPresenter(CustomerOrdersContract.View view) {
        this.view = view;
        repository = new CustomerOrdersRepositoryImpl(this);
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
    public void onOrderAdded(CustomerOrder customerOrder) {
        Log.i(TAG, "CustomerOrder " + customerOrder.toString() + " added");
        view.addOrder(customerOrder);
    }

    @Override
    public void onOrderChanged(CustomerOrder customerOrder) {
        view.updateOrder(customerOrder);
    }

    @Override
    public void onOrderRemoved(String key) {

    }
}
