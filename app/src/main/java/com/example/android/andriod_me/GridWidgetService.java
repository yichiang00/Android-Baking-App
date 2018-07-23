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

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.android_me.R;
import com.example.android.android_me.data.ReceiptQueryTask;
import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.ui.MasterListFragment;
import com.example.android.android_me.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;


public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    Cursor mCursor;
//    RemoteViews mViews;
    ArrayList<Receipt> mReceipts = new ArrayList<Receipt>();
    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;

    }

    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        methodThatStartsTheAsyncTask();

    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.receipt_widget);
        views.setTextViewText(R.id.recept_widget_title, mReceipts.get(position).getName());


        return views;

    }
    /* Skipping most code and I will only show you the most essential. */
    private void methodThatStartsTheAsyncTask()
    {
        URL searchURL = NetworkUtils.buildUrl(NetworkUtils.RECEIPT_URL);
        ReceiptQueryTask testAsyncTask = new ReceiptQueryTask(new MasterListFragment.FragmentCallback() {

            @Override
            public void onTaskDone(ArrayList<Receipt> r) {
                methodThatDoesSomethingWhenTaskIsDone(r);

            }
        });

        testAsyncTask.execute(searchURL);

    }

    private void methodThatDoesSomethingWhenTaskIsDone(ArrayList<Receipt> r)
    {
        /* Magic! */
        if(r == null)
        {
            r = new ArrayList<Receipt>();
        }
        mReceipts=r;

    }

    public interface FragmentCallback {
        public void onTaskDone(ArrayList<Receipt> r);
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

