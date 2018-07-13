package ardjomand.leonardo.nutrimeal.companyorders;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.CompanyOrdersRepository;
import ardjomand.leonardo.nutrimeal.data.CompanyOrdersRepositoryImpl;

public class CompanyOrdersPresenter implements CompanyOrdersContract.Presenter, CompanyOrdersRepository.Presenter {

    private static final String TAG = CompanyOrdersPresenter.class.getSimpleName();
    private final CompanyOrdersRepository.Repository repository;
    private CompanyOrdersContract.View view;

    CompanyOrdersPresenter(CompanyOrdersContract.View view) {
        this.view = view;
        repository = new CompanyOrdersRepositoryImpl(this);
    }

    @Override
    public void setView(CompanyOrdersContract.View view) {
        this.view = view;
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
        if (view != null){
            view.addOrder(companyOrder);
        }
    }

    @Override
    public void onOrderChanged(CompanyOrder companyOrder) {
        if (view != null){
            view.updateOrder(companyOrder);
        }
    }

    @Override
    public void onOrderRemoved(String key) {

    }
}
