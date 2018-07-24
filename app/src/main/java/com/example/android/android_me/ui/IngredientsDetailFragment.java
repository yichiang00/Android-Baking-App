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
import com.example.android.android_me.model.Ingredient;
import com.example.android.android_me.model.Step;
import java.util.ArrayList;


public class IngredientsDetailFragment extends Fragment {

    private GridView mGridView;
    public RecyclerView mRecyclerView;
    public IngedientListAdapter mAdapter;
    public LinearLayoutManager mLayoutManager;
    ArrayList<Ingredient> mIngredients =  new ArrayList<Ingredient>();



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }
    public void setData(ArrayList<Ingredient> sIngredient)
    {
        mIngredients = sIngredient;
    }


    public IngredientsDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_ingredient_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.images_grid_view);

        mAdapter = new IngedientListAdapter();
        mLayoutManager =
                new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Set the adapter on the GridView
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItems(mIngredients);
        mAdapter.notifyDataSetChanged();




        return rootView;
    }



}
