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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.android_me.R;
import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.model.Step;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements StepListFragment.OnImageClickListener{
    public static final String RECEIPT_DATA = "cReceipt";
    private Receipt mReceipt = new Receipt();
    private Integer selectedIndex = 0;
    private boolean mTwoPane;
    StepDetailFragment mStepDetailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null) {

            if(getIntent().getExtras().getSerializable(RECEIPT_DATA) != null) {
                mReceipt = (Receipt) getIntent().getSerializableExtra(RECEIPT_DATA);
            }

        }else
        {
            mReceipt = (Receipt) savedInstanceState.getSerializable(RECEIPT_DATA);
        }

        ReceiptNameCardFragment headFragment = new ReceiptNameCardFragment();

        ArrayList<String> allTitles =new ArrayList<String>();
        ArrayList<Step> steps =  mReceipt.getSteps();
        for (int i =0;i < steps.size(); i++)
        {
            allTitles.add(steps.get(i).getShortDescription());
        }
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.android_me_linear_layout) != null) {
            mTwoPane = true;


            mStepDetailFragment = new StepDetailFragment();
            mStepDetailFragment.setData(steps);
            mStepDetailFragment.setListIndex(selectedIndex);
            fragmentManager.beginTransaction()
                    .add(R.id.master_detail_step_fragment, mStepDetailFragment)
                    .commit();
        }
        StepListFragment stepListFragment = new StepListFragment();
        fragmentManager.beginTransaction()
                .add(R.id.master_list_step_fragment, stepListFragment)
                .commit();


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(RECEIPT_DATA, mReceipt);

        super.onSaveInstanceState(savedInstanceState);
    }
    public ArrayList<Step> getMyData() {
        return mReceipt.getSteps();
    }
    public void onImageSelected(int position) {
        if (mTwoPane) {

            mStepDetailFragment = new StepDetailFragment();
            mStepDetailFragment.setData(mReceipt.getSteps());
            selectedIndex = position;
            mStepDetailFragment.setListIndex(selectedIndex);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.master_detail_step_fragment, mStepDetailFragment)
                    .commit();

        } else {
            final Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(RECEIPT_DATA, mReceipt);
            intent.putExtra(StepDetailActivity.STEP_DATA, mReceipt.getSteps());
            intent.putExtra(StepDetailActivity.INDEX_STEP_DATA, position);
            startActivity(intent);

        }
    }


}
