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

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;
import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// This activity will display a custom Android image composed of three body parts: head, body, and legs
public class DetailActivity extends AppCompatActivity implements StepListFragment.OnImageClickListener{
    public static final String RECEIPT_DATA = "cReceipt";
    public static final String RECEIPT_STEP_DATA = "cReceiptStep";
    private Receipt mReceipt = new Receipt();

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(getIntent().getExtras().getSerializable(RECEIPT_DATA) != null) {
            mReceipt = (Receipt) getIntent().getSerializableExtra(RECEIPT_DATA);
            // Only create new fragments when there is no previously saved state
            if(savedInstanceState == null) {

                // Retrieve list index values that were sent through an intent; use them to display the desired Android-Me body part image
                // Use setListindex(int index) to set the list index for all BodyPartFragments

                // Create a new head BodyPartFragment
                ReceiptNameCardFragment headFragment = new ReceiptNameCardFragment();

                ArrayList<String> allTitles =new ArrayList<String>();
                ArrayList<Step> steps =  mReceipt.getSteps();
                for (int i =0;i < steps.size(); i++)
                {
                    allTitles.add(steps.get(i).getShortDescription());
                }
                FragmentManager fragmentManager = getSupportFragmentManager();

                if(findViewById(R.id.android_me_linear_layout) != null) {
                    // This LinearLayout will only initially exist in the two-pane tablet case
                    mTwoPane = true;
                    // Set the list of image id's for the head fragment and set the position to the second image in the list
                    headFragment.setImageIds(allTitles);

                    // Get the correct index to access in the array of head images from the intent
                    // Set the default value to 0
                    int headIndex = getIntent().getIntExtra("headIndex", 0);
                    headFragment.setListIndex(headIndex);

                    // Add the fragment to its container using a FragmentManager and a Transaction

                    fragmentManager.beginTransaction()
                            .add(R.id.head_container, headFragment)
                            .commit();
                }
                StepListFragment stepListFragment = new StepListFragment();
//                stepListFragment.setData(mReceipt.getSteps());
                fragmentManager.beginTransaction()
                        .add(R.id.master_list_step_fragment, stepListFragment)
                        .commit();


            }
        }



    }
    public ArrayList<Step> getMyData() {
        return mReceipt.getSteps();
    }
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was clicked

    }

}
