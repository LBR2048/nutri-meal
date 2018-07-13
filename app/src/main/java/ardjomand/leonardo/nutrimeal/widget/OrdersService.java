package ardjomand.leonardo.nutrimeal.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import ardjomand.leonardo.nutrimeal.BuildConfig;

/**
 * Created by unity on 09/01/18.
 */

public class OrdersService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        if (BuildConfig.FLAVOR.equals("company")) {
            return new CompanyOrdersRemoteViewsFactory(this.getApplicationContext(), intent);
        } else {
            return new CustomerOrdersRemoteViewsFactory(this.getApplicationContext(), intent);
        }
    }
}
