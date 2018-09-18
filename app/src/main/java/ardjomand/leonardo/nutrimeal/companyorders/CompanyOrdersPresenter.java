package ardjomand.leonardo.nutrimeal.companyorders;

import android.util.Log;

import ardjomand.leonardo.nutrimeal.data.common.GenericRepository;
import ardjomand.leonardo.nutrimeal.data.common.GenericRepositoryImpl;
import ardjomand.leonardo.nutrimeal.data.pojos.CompanyOrder;

public class CompanyOrdersPresenter implements
        CompanyOrdersContract.Presenter,
        GenericRepository.Presenter<CompanyOrder> {

    private static final String TAG = CompanyOrdersPresenter.class.getSimpleName();
    private final GenericRepository.Repository repository;
    private CompanyOrdersContract.View view;

    CompanyOrdersPresenter(CompanyOrdersContract.View view) {
        this.view = view;
        repository = new GenericRepositoryImpl<>(this, CompanyOrder.class);
    }

    @Override
    public void setView(CompanyOrdersContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }

    @Override
    public void onItemAdded(CompanyOrder item) {
        Log.i(TAG, "CompanyOrder " + item.toString() + " added");
        if (view != null){
            view.addItem(item);
        }
    }

    @Override
    public void onItemChanged(CompanyOrder item) {
        if (view != null){
            view.updateItem(item);
        }
    }

    @Override
    public void onItemRemoved(String key) {

    }
}
