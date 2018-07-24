package com.example.android.andriod_me;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.android.android_me.R;
import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.ui.DetailActivity;
import com.example.android.android_me.ui.MainActivity;
import com.google.gson.Gson;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
//        views.setTextViewText(R.id.appwidget_text, "It was a simple text");
        RemoteViews rv= getReceiptGridRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }



    private static RemoteViews getReceiptGridRemoteView(Context context) {
        //https://stackoverflow.com/questions/5184169/android-widget-configuration-with-sharedpreferences
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);

        String receiptJson="";
        receiptJson = PreferenceManager.getDefaultSharedPreferences(context).getString(Integer.toString(R.string.prf_receipt), receiptJson);
        Gson gson = new Gson();
        Receipt mReceipt = gson.fromJson(receiptJson, Receipt.class);
        if(mReceipt != null)
        {
            views.setTextViewText(R.id.appwidget_text, mReceipt.getName());

            Intent intent = new Intent(context, GridWidgetService.class);
            views.setRemoteAdapter(R.id.widget_grid_view, intent);

            Intent appIntent = new Intent(context, DetailActivity.class);
            //appIntent.put~
            PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);
            // Handle empty gardens
            views.setEmptyView(R.id.widget_grid_view, R.id.empty_view);
        }else {
            Intent appIntent = new Intent(context, MainActivity.class);


        }

        return views;
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
//            String receiptJson="";
//            receiptJson = PreferenceManager.getDefaultSharedPreferences(context).getString("favReceipt", receiptJson);
//            Gson gson = new Gson();
//            Receipt mReceipt = gson.fromJson(receiptJson, Receipt.class);
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateRecceiptWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

}

