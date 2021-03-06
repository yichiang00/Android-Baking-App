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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.example.android.android_me.R;
import com.example.android.android_me.model.Step;
import java.util.ArrayList;


public class StepListFragment extends Fragment {

    StepListFragment.OnImageClickListener mCallback;
    StepListFragment.OnIngredientsBtnClickerListener mIngredientsBtnmCallback;
    private GridView mGridView;
    public RecyclerView mRecyclerView;
    public StepListAdapter mAdapter;
    public LinearLayoutManager mLayoutManager;
    ArrayList<Step> steps =  new ArrayList<Step>();
    public interface OnImageClickListener {
        void onImageSelected(int position);
    }
    public interface OnIngredientsBtnClickerListener {
        void onIngredientBtnClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnImageClickListener) context;
            mIngredientsBtnmCallback = (OnIngredientsBtnClickerListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


    public StepListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_step_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.images_grid_view);
        DetailActivity activity = (DetailActivity) getActivity();
        steps = activity.getMyData();
        mAdapter = new StepListAdapter(mCallback);
        mLayoutManager =
                new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Set the adapter on the GridView
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItems(steps);
        mAdapter.notifyDataSetChanged();


        Button IngredientsBtn = (Button) rootView.findViewById(R.id.receipt_ingredients);
        IngredientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIngredientsBtnmCallback.onIngredientBtnClicked();


            }
        });


        return rootView;
    }



}
