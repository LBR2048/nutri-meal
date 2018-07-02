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
import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrder;
import ardjomand.leonardo.nutrimeal.data.CompanyWidgetRepository;
import ardjomand.leonardo.nutrimeal.data.CompanyWidgetRepositoryImpl;

/**
 * Created by unity on 09/01/18.
 */

public class CompanyOrdersRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private final int mAppWidgetId;
    private CompanyWidgetRepository repository;

    private List<CompanyOrder> mCompanyOrders = new ArrayList<>();
    private RemoteViews views;
    private CountDownLatch mCountDownLatch;

    public CompanyOrdersRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        repository = new CompanyWidgetRepositoryImpl();
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
        return mCompanyOrders.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        views = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        CompanyOrder companyOrder = mCompanyOrders.get(i);
        views.setTextViewText(android.R.id.text1, Utils.formatCompanyOrder(mContext, companyOrder));
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
        repository.loadCompanyOrders(new CompanyWidgetRepository.LoadCompanyOrdersCallback() {
            @Override
            public void onComplete(List<CompanyOrder> companyOrders) {
                mCompanyOrders = companyOrders;
                mCountDownLatch.countDown();
            }
        });
    }

    private void loadDummyData() {
        CompanyOrder companyOrder = new CompanyOrder();
        companyOrder.setKey("company order 0");
        CompanyOrder companyOrder1 = new CompanyOrder();
        companyOrder1.setKey("company order 1");
        mCompanyOrders.add(companyOrder);
        mCompanyOrders.add(companyOrder1);
    }

}
