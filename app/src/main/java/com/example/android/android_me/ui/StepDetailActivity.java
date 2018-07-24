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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.android.android_me.R;
import com.example.android.android_me.model.Ingredient;
import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.model.Step;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity implements StepDetailFragment.OnButtonClickListener{
    public static final String INGREDIENT_DATA = "sIngredient";
    public static final String IS_INGREDIENT = "isGredient";
    public static final String STEP_DATA = "sStep";
    public static final String INDEX_STEP_DATA = "sStep_index";
    private ArrayList<Step> mSteps = new ArrayList<Step>();
    private ArrayList<Ingredient> mIngredient = new ArrayList<Ingredient>();
    private Integer selectedIndex = 0;
    private Receipt mReceipt;
    private boolean isIngriedentView = true;
    FragmentManager mFragmentManager;
    StepDetailFragment mStepListFragment;
    IngredientsDetailFragment mIngredientsDetailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);
        if(getIntent().getExtras().getSerializable(STEP_DATA) != null) {
            mReceipt = (Receipt) getIntent().getSerializableExtra(DetailActivity.RECEIPT_DATA);
            mSteps = (ArrayList<Step>) getIntent().getSerializableExtra(STEP_DATA);
            selectedIndex = getIntent().getIntExtra(INDEX_STEP_DATA, 0);
            mIngredient = (ArrayList<Ingredient>) getIntent().getSerializableExtra(INGREDIENT_DATA);
            isIngriedentView = getIntent().getBooleanExtra(IS_INGREDIENT, false);

            if(savedInstanceState == null) {



                mFragmentManager = getSupportFragmentManager();
                if(!isIngriedentView){
                    mStepListFragment = new StepDetailFragment();
                    mStepListFragment.setData(mSteps);
                    mStepListFragment.setListIndex(selectedIndex);
                    mFragmentManager.beginTransaction()
                            .add(R.id.master_detail_step_fragment, mStepListFragment)
                            // Add this transaction to the back stack
                            .addToBackStack(null)
                            .commit();

                }else{
                    mIngredientsDetailFragment = new IngredientsDetailFragment();
                    mIngredientsDetailFragment.setData(mIngredient);
                    mFragmentManager.beginTransaction()
                            .add(R.id.master_detail_step_fragment, mIngredientsDetailFragment)
                            // Add this transaction to the back stack
                            .addToBackStack(null)
                            .commit();

                }




            }
        }


    }
    public void onButtonClicked(int position) {
        selectedIndex = position;
        mStepListFragment = new StepDetailFragment();
        mStepListFragment.setData(mSteps);
        mStepListFragment.setListIndex(selectedIndex);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.master_detail_step_fragment, mStepListFragment)
                .commit();
    }
    @Override
    public void onBackPressed()
    {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }else {
            final Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.RECEIPT_DATA, mReceipt);
            setResult(RESULT_OK, intent);
        }

    }
//    public void onIngredientBtnClicked() {
//    }

}
