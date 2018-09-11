package ardjomand.leonardo.nutrimeal.companyorders;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.CompanyOrdersRepository;
import ardjomand.leonardo.nutrimeal.data.CompanyOrdersRepositoryImpl;

public class CompanyOrdersPresenter implements
        CompanyOrdersContract.Presenter,
        CompanyOrdersRepository.Presenter<CompanyOrder> {

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
        repository.subscribe();
    }

    @Override
    public void unsubscribeFromOrdersUpdates() {
        repository.unsubscribe();
    }

    @Override
    public void onItemAdded(CompanyOrder item) {
        Log.i(TAG, "CompanyOrder " + item.toString() + " added");
        if (view != null){
            view.addOrder(item);
        }
    }

    @Override
    public void onItemChanged(CompanyOrder item) {
        if (view != null){
            view.updateOrder(item);
        }
    }

    @Override
    public void onItemRemoved(String key) {

    }
}
