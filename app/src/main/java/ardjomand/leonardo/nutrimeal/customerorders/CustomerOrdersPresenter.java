package ardjomand.leonardo.nutrimeal.customerorders;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.common.GenericRepository;
import ardjomand.leonardo.nutrimeal.data.common.GenericRepositoryImpl;
import ardjomand.leonardo.nutrimeal.data.pojos.CustomerOrder;

public class CustomerOrdersPresenter implements
        CustomerOrdersContract.Presenter,
        GenericRepository.Presenter<CustomerOrder> {

    private static final String TAG = CustomerOrdersPresenter.class.getSimpleName();
    private final GenericRepository.Repository repository;
    private CustomerOrdersContract.View view;

    CustomerOrdersPresenter(CustomerOrdersContract.View view) {
        this.view = view;
        repository = new GenericRepositoryImpl<>(this, CustomerOrder.class);
    }

    @Override
    public void setView(CustomerOrdersContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribeToOrdersUpdates() {
        repository.subscribe();
    }

    @Override
    public void unsubscribeFromOrdersUpdates() {
        repository.unsubscribe();
    }

    @Override
    public void onItemAdded(CustomerOrder item) {
        Log.i(TAG, "CustomerOrder " + item.toString() + " added");
        if (view != null){
            view.addOrder(item);
        }
    }

    @Override
    public void onItemChanged(CustomerOrder customerOrder) {
        if (view != null){
            view.updateOrder(customerOrder);
        }
    }

    @Override
    public void onItemRemoved(String key) {

    }
}
