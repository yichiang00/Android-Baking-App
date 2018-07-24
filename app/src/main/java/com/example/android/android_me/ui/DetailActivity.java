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

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.andriod_me.BakingAppWidgetProvider;
import com.example.android.andriod_me.ReceiptService;
import com.example.android.android_me.R;
import com.example.android.android_me.model.Ingredient;
import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.model.Step;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements StepListFragment.OnImageClickListener,
        StepDetailFragment.OnButtonClickListener, StepListFragment.OnIngredientsBtnClickerListener,
        SharedPreferences.OnSharedPreferenceChangeListener
{
    public static final String RECEIPT_DATA = "cReceipt";
    private Receipt mReceipt = new Receipt();
    private Integer selectedIndex = 0;
    private boolean mTwoPane;
    StepDetailFragment mStepDetailFragment;
    IngredientsDetailFragment mIngredientsDetailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null) {

            if(getIntent().getExtras() != null && getIntent().getExtras().getSerializable(RECEIPT_DATA) != null) {
                mReceipt = (Receipt) getIntent().getSerializableExtra(RECEIPT_DATA);
            }else{
                String receiptJson = PreferenceManager.getDefaultSharedPreferences(this).getString("favReceipt", "");
                if(receiptJson != null && receiptJson.length() > 0){
                    Gson gson = new Gson();
                    mReceipt = gson.fromJson(receiptJson, Receipt.class);
                }
            }

        }else
        {
            mReceipt = (Receipt) savedInstanceState.getSerializable(RECEIPT_DATA);
        }
        String receiptJson = new Gson().toJson(mReceipt);
        //https://stackoverflow.com/questions/7145606/how-android-sharedpreferences-save-store-object
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString(Integer.toString(R.string.prf_receipt), receiptJson).apply();

        // TRY TO RETRIVE

//        receiptJson = PreferenceManager.getDefaultSharedPreferences(this).getString("favReceipt", receiptJson);
//        Gson gson = new Gson();
//        Receipt mReceipt = gson.fromJson(receiptJson, Receipt.class);
        if(mReceipt != null) {
            ReceiptNameCardFragment headFragment = new ReceiptNameCardFragment();

            ArrayList<String> allTitles = new ArrayList<String>();
            ArrayList<Step> steps = mReceipt.getSteps();
            if(steps != null){
                for (int i = 0; i < steps.size(); i++) {
                    allTitles.add(steps.get(i).getShortDescription());
                }
                FragmentManager fragmentManager = getSupportFragmentManager();

                if (findViewById(R.id.android_me_linear_layout) != null) {
                    mTwoPane = true;

                    mIngredientsDetailFragment = new IngredientsDetailFragment();
                    fragmentManager.beginTransaction()
                            .add(R.id.master_detail_indgedient_fragment, mIngredientsDetailFragment)
                            .commit();

//                    mStepDetailFragment = new StepDetailFragment();
//                    mStepDetailFragment.setData(steps);
//                    mStepDetailFragment.setListIndex(selectedIndex);
//                    fragmentManager.beginTransaction()
//                            .add(R.id.master_detail_step_fragment, mStepDetailFragment)
//                            .commit();
                }

                StepListFragment stepListFragment = new StepListFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.master_list_step_fragment, stepListFragment)
                        .commit();
            }

        }


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(RECEIPT_DATA, mReceipt);

        super.onSaveInstanceState(savedInstanceState);
    }
    public ArrayList<Step> getMyData() {
        return mReceipt.getSteps();
    }
    public ArrayList<Ingredient> getIngredients() {
        return mReceipt.getIngredients();
    }
    public void onImageSelected(int position) {
        ChangeViewByPosition(position);
    }
    public void ChangeViewByPosition(int position){
        selectedIndex = position;
        if (mTwoPane) {
            mStepDetailFragment = new StepDetailFragment();
            mStepDetailFragment.setData(mReceipt.getSteps());
            mStepDetailFragment.setListIndex(selectedIndex);
            getSupportFragmentManager().beginTransaction()
                    .remove(mIngredientsDetailFragment)
                    .replace(R.id.master_detail_step_fragment, mStepDetailFragment)
                    .commit();

        } else {
            final Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(RECEIPT_DATA, mReceipt);
            intent.putExtra(StepDetailActivity.STEP_DATA, mReceipt.getSteps());
            intent.putExtra(StepDetailActivity.INDEX_STEP_DATA, selectedIndex);
            intent.putExtra(StepDetailActivity.INGREDIENT_DATA, mReceipt.getIngredients());
            intent.putExtra(StepDetailActivity.IS_INGREDIENT, false);
            startActivity(intent);

        }
    }
    public void onButtonClicked(int position) {
        ChangeViewByPosition(position);
    }

    public void onIngredientBtnClicked() {
        if (mTwoPane) {
            mIngredientsDetailFragment = new IngredientsDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .remove(mStepDetailFragment)
                    .add(R.id.master_detail_indgedient_fragment, mIngredientsDetailFragment)
                    .commit();

        } else {
            final Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(RECEIPT_DATA, mReceipt);
            intent.putExtra(StepDetailActivity.STEP_DATA, mReceipt.getSteps());
            intent.putExtra(StepDetailActivity.INDEX_STEP_DATA, selectedIndex);
            intent.putExtra(StepDetailActivity.INGREDIENT_DATA, mReceipt.getIngredients());
            intent.putExtra(StepDetailActivity.IS_INGREDIENT, true);
            startActivity(intent);

        }
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        ReceiptService.startActionUpdateReceiptWidgets(this);


    }



}
