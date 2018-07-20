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

package com.example.android.android_me.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;
import com.example.android.android_me.data.ReceiptQueryTask;
import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.utilities.JsonUtils;
import com.example.android.android_me.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


// This fragment displays all of the AndroidMe images in one large list
// The list appears as a grid of images
public class MasterListFragment extends Fragment {

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnImageClickListener mCallback;
    private RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager  mLayoutManager;
    public MasterListAdapter mAdapter;
    ArrayList<Receipt> receipts =  new ArrayList<Receipt>();
    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnImageClickListener {
        void onImageSelected(int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


    // Mandatory empty constructor
    public MasterListFragment() {
    }

    public void setData(ArrayList<Receipt> sReceipts){
        receipts = sReceipts;
    }
    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.images_grid_view);
        mLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);



        methodThatStartsTheAsyncTask();
        // Return the root view
        return rootView;
    }
    /* Skipping most code and I will only show you the most essential. */
    private void methodThatStartsTheAsyncTask()
    {
        URL searchURL = NetworkUtils.buildUrl(NetworkUtils.RECEIPT_URL);
        ReceiptQueryTask testAsyncTask = new ReceiptQueryTask(new FragmentCallback() {

            @Override
            public void onTaskDone(ArrayList<Receipt> r) {
                methodThatDoesSomethingWhenTaskIsDone(r);
            }
        });

        testAsyncTask.execute(searchURL);

//        testAsyncTask.execute(NetworkUtils.RECEIPT_URL);
    }

    private void methodThatDoesSomethingWhenTaskIsDone(ArrayList<Receipt> r)
    {
        /* Magic! */
        if(r == null)
        {
            r = new ArrayList<Receipt>();
        }
        Receipt t = new Receipt();
        t.setName("Receipt One");
        t.setImage("https://i.stack.imgur.com/1PBvA.jpg?s=32&g=1");
        r.add(t);
        mAdapter = new MasterListAdapter( getActivity(), r);
        mAdapter.setReceiptItems(r);
    }

    public interface FragmentCallback {
        public void onTaskDone(ArrayList<Receipt> r);
    }
}
