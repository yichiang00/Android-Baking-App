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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.android_me.R;
import com.example.android.android_me.model.Step;

import java.util.ArrayList;


// This fragment displays all of the AndroidMe images in one large list
// The list appears as a grid of images
public class StepDetailFragment extends Fragment {
    public static final String STEP_ID_LIST = "steps_ids";
    public static final String LIST_INDEX = "list_index";

    ArrayList<Step> mSteps;
    Integer mListIndex = 0;

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    public void setData(ArrayList<Step> sSteps)
    {
        mSteps = sSteps;
    }
    public void setListIndex(Integer index)
    {
        mListIndex = index;
    }


    // Mandatory empty constructor
    public StepDetailFragment() {
    }


    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_detail_step, container, false);


        if(savedInstanceState != null) {
            mSteps = (ArrayList<Step>) savedInstanceState.getSerializable(STEP_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }
        TextView descTextView = rootView.findViewById(R.id.receipt_step_description);
        descTextView.setText(mSteps.get(mListIndex).getDescription());
        StepDetailActivity activity = (StepDetailActivity) getActivity();




        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putSerializable(STEP_ID_LIST, mSteps);
        currentState.putInt(LIST_INDEX, mListIndex);
    }

}
