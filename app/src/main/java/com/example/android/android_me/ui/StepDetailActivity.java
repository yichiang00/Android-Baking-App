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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;
import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.model.Step;

import java.util.ArrayList;

// This activity will display a custom Android image composed of three body parts: head, body, and legs
public class StepDetailActivity extends AppCompatActivity implements StepListFragment.OnImageClickListener{
    public static final String STEP_DATA = "sStep";
    public static final String INDEX_STEP_DATA = "sStep_index";
    private ArrayList<Step> steps = new ArrayList<Step>();
    private Integer selectedIndex = 0;
    private Receipt mReceipt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);
        if(getIntent().getExtras().getSerializable(STEP_DATA) != null) {
            mReceipt = (Receipt) getIntent().getSerializableExtra(DetailActivity.RECEIPT_DATA);
            steps = (ArrayList<Step>) getIntent().getSerializableExtra(STEP_DATA);
            selectedIndex = getIntent().getIntExtra(INDEX_STEP_DATA, 0);
            // Only create new fragments when there is no previously saved state
            if(savedInstanceState == null) {

                // Retrieve list index values that were sent through an intent; use them to display the desired Android-Me body part image
                // Use setListindex(int index) to set the list index for all BodyPartFragments

                // Create a new head BodyPartFragment
                ReceiptNameCardFragment headFragment = new ReceiptNameCardFragment();

                ArrayList<String> allTitles =new ArrayList<String>();

                FragmentManager fragmentManager = getSupportFragmentManager();


                StepDetailFragment stepListFragment = new StepDetailFragment();
                stepListFragment.setData(steps);
                stepListFragment.setListIndex(selectedIndex);
                fragmentManager.beginTransaction()
                        .add(R.id.master_detail_step_fragment, stepListFragment)
                        .commit();


            }
        }



    }
    public void onImageSelected(int position) {

    }
    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        // add data to Intent
        data.putExtra(DetailActivity.RECEIPT_DATA,mReceipt);
        setResult(Activity.RESULT_OK, data);
        super.onBackPressed();
    }
}
