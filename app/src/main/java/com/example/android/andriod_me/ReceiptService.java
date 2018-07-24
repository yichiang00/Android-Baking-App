package com.example.android.andriod_me;

/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.android_me.R;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class ReceiptService extends IntentService {

//    public static final String ACTION_WATER_PLANT = "com.example.android.mygarden.action.water_plant";
      public static final String ACTION_UPDATE_RECEIPT_WIDGETS = "com.example.android.andriod_me.action.update_receipt_widgets";
//    public static final String EXTRA_PLANT_ID = "com.example.android.mygarden.extra.PLANT_ID";;

    public ReceiptService() {
        super("PlantWateringService");
    }

    /**
     * Starts this service to perform WaterPlant action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
//    public static void startActionWaterPlant(Context context, long plantId) {
//        Intent intent = new Intent(context, ReceiptService.class);
//        intent.setAction(ACTION_WATER_PLANT);
//        intent.putExtra(EXTRA_PLANT_ID, plantId);
//        context.startService(intent);
//    }

    /**
     * Starts this service to perform UpdatePlantWidgets action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateReceiptWidgets(Context context) {
        Intent intent = new Intent(context, ReceiptService.class);
        intent.setAction(ACTION_UPDATE_RECEIPT_WIDGETS);
        context.startService(intent);
    }

    /**
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECEIPT_WIDGETS.equals(action)) {
                handleActionUpdatePlantWidgets();
            }
        }
    }

    private void handleActionUpdatePlantWidgets() {
        //Query to get the plant that's most in need for water (last watered)

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        //Now update all widgets
        BakingAppWidgetProvider.updateRecceiptWidgets(this, appWidgetManager, appWidgetIds);


    }


}
