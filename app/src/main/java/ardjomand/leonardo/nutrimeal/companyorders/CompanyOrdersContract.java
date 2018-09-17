package ardjomand.leonardo.nutrimeal.companyorders;


import ardjomand.leonardo.nutrimeal.ObserverPresenter;
import ardjomand.leonardo.nutrimeal.ObserverView;

interface CompanyOrdersContract {

    interface View<T> extends ObserverView<T> { }

    interface Presenter extends ObserverPresenter{

        void setView(View view);
    }
}
