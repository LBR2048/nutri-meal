package ardjomand.leonardo.nutrimeal.companyorders;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.CompanyOrdersRepository;
import ardjomand.leonardo.nutrimeal.data.CompanyOrdersRepositoryImpl;

public class CompanyOrdersPresenter implements CompanyOrdersContract.Presenter, CompanyOrdersRepository.Presenter {

    public static final String TAG = CompanyOrdersPresenter.class.getSimpleName();
    private final CompanyOrdersRepository.Repository repository;
    private CompanyOrdersContract.View view;

    public CompanyOrdersPresenter(CompanyOrdersContract.View view) {
        this.view = view;
        repository = new CompanyOrdersRepositoryImpl(this);
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
    public void onOrderAdded(CompanyOrder companyOrder) {
        Log.i(TAG, "CompanyOrder " + companyOrder.toString() + " added");
        view.addOrder(companyOrder);
    }

    @Override
    public void onOrderChanged(CompanyOrder companyOrder) {
        view.updateOrder(companyOrder);
    }

    @Override
    public void onOrderRemoved(String key) {

    }
}
