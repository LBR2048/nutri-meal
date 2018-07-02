package ardjomand.leonardo.nutrimeal.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import ardjomand.leonardo.nutrimeal.Utils;
import ardjomand.leonardo.nutrimeal.customerorders.CustomerOrder;
import ardjomand.leonardo.nutrimeal.data.CustomerWidgetRepository;
import ardjomand.leonardo.nutrimeal.data.CustomerWidgetRepositoryImpl;

/**
 * Created by unity on 09/01/18.
 */

public class CustomerOrdersRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private final int mAppWidgetId;
    private CustomerWidgetRepository repository;

    private List<CustomerOrder> mCustomerOrders = new ArrayList<>();
    private RemoteViews views;
    private CountDownLatch mCountDownLatch;

    public CustomerOrdersRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        repository = new CustomerWidgetRepositoryImpl();
    }

    @Override
    public void onDataSetChanged() {
        //https://github.com/mvescovo/item-reaper/blob/master/app/src/main/java/com/michaelvescovo/android/itemreaper/widget/WidgetListService.java
        mCountDownLatch = new CountDownLatch(1);
        loadData();
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        loadDummyData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCustomerOrders.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        views = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        CustomerOrder customerOrder = mCustomerOrders.get(i);
        views.setTextViewText(android.R.id.text1, Utils.formatCustomerOrder(mContext, customerOrder));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void loadData() {
        repository.loadCustomerOrders(new CustomerWidgetRepository.LoadCustomersOrdersCallback() {
            @Override
            public void onComplete(List<CustomerOrder> customerOrders) {
                mCustomerOrders = customerOrders;
                mCountDownLatch.countDown();
            }
        });
    }

    private void loadDummyData() {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setKey("order 0");
        CustomerOrder customerOrder1 = new CustomerOrder();
        customerOrder1.setKey("order 1");
        mCustomerOrders.add(customerOrder);
        mCustomerOrders.add(customerOrder1);
    }

}
