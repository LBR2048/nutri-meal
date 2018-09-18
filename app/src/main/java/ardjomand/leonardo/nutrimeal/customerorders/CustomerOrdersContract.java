package ardjomand.leonardo.nutrimeal.customerorders;

import ardjomand.leonardo.nutrimeal.ObserverPresenter;
import ardjomand.leonardo.nutrimeal.ObserverView;

interface CustomerOrdersContract {

    interface View<T> extends ObserverView<T> { }

    interface Presenter extends ObserverPresenter {

        void setView(View view);
    }
}
