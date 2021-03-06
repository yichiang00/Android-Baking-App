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

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.android_me.R;
import com.example.android.android_me.data.ReceiptQueryTask;
import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.ui.MasterListFragment;
import com.example.android.android_me.utilities.NetworkUtils;
import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;


public class GridWidgetService extends RemoteViewsService {
    GridRemoteViewsFactory mGridRemoteViewsFactory;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        mGridRemoteViewsFactory = new GridRemoteViewsFactory(this.getApplicationContext());
        return mGridRemoteViewsFactory;
    }

}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    Receipt mReceipt;
    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
//        mReceipts = rReceipts;
        String receiptJson="";
        receiptJson = PreferenceManager.getDefaultSharedPreferences(mContext).getString(Integer.toString(R.string.prf_receipt), receiptJson);
        Gson gson = new Gson();
        mReceipt = gson.fromJson(receiptJson, Receipt.class);
    }

    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {

        String receiptJson="";
        receiptJson = PreferenceManager.getDefaultSharedPreferences(mContext).getString(Integer.toString(R.string.prf_receipt), receiptJson);
        Gson gson = new Gson();
        mReceipt = gson.fromJson(receiptJson, Receipt.class);
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mReceipt != null && mReceipt.getIngredients() != null){
            return mReceipt.getIngredients().size();
        }else{
            return 0;
        }
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.fragment_detail_ingredients_widget);
        views.setTextViewText(R.id.receipt_ingredient_name, mReceipt.getIngredients().get(position).getIngredient());
        views.setTextViewText(R.id.receipt_ingredient_quantity, Float.toString(mReceipt.getIngredients().get(position).getQuantity()));
        views.setTextViewText(R.id.receipt_ingredient_measure, mReceipt.getIngredients().get(position).getMeasure());


        return views;

    }




    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

