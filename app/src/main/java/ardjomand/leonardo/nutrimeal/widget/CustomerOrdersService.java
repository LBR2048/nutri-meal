package ardjomand.leonardo.nutrimeal.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by unity on 09/01/18.
 */

public class CustomerOrdersService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CustomerOrdersRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
