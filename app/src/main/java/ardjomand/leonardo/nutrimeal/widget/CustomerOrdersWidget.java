package ardjomand.leonardo.nutrimeal.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import ardjomand.leonardo.nutrimeal.BuildConfig;
import ardjomand.leonardo.nutrimeal.MainActivity;
import ardjomand.leonardo.nutrimeal.R;

/**
 * Implementation of App Widget functionality.
 */
public class CustomerOrdersWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int i = 0; i < appWidgetIds.length; ++i) {

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.customer_orders_widget);

            // Set the ListWidgetService intent to act as the adapter for the ListView
            Intent listWidgetIntent = new Intent(context, CustomerOrdersService.class);
            listWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            listWidgetIntent.setData(Uri.parse(listWidgetIntent.toUri(Intent.URI_INTENT_SCHEME)));

            views.setRemoteAdapter(R.id.appwidget_ingredients_listview, listWidgetIntent);

            // Create an intent to launch MainActivity when clicked
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Widget allow click handlers to only launch pending intents
            views.setOnClickPendingIntent(R.id.appwidget, pendingIntent);

            // TODO improve using strings.xml
            // Show title on the widget
            if (BuildConfig.FLAVOR.equals("company")) {
                views.setTextViewText(R.id.appwidget_title, "Orders");
            } else {
                views.setTextViewText(R.id.appwidget_title, "My Orders");
            }

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetIds[i], views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
