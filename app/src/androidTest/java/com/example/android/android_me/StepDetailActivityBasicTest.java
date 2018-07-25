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

package com.example.android.android_me;


import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;

import com.example.android.android_me.model.Ingredient;
import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.model.Step;
import com.example.android.android_me.ui.DetailActivity;
import com.example.android.android_me.ui.IngredientsDetailFragment;
import com.example.android.android_me.ui.StepDetailActivity;
import com.example.android.android_me.ui.StepDetailFragment;
import com.example.android.android_me.ui.StepListFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * This test demos a user clicking the decrement button and verifying that it properly decrease
 * the quantity the total cost.
 */
//https://android.jlelse.eu/the-basics-of-android-espresso-testing-activities-fragments-7a8bfbc16dc5

@RunWith(AndroidJUnit4.class)
public class StepDetailActivityBasicTest {

    @Rule
    public ActivityTestRule<StepDetailActivity> mActivityTestRule =
            new ActivityTestRule<StepDetailActivity>(StepDetailActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    //load intent first
                    Receipt testReceipt = new Receipt();
                    testReceipt.setName("myTestReceipt");

                    ArrayList<Step> steps = new ArrayList<>();
                    Step step1 = new Step();
                    step1.setShortDescription("Step 1 shortDescription");
                    step1.setDescription("Step 1 description");

                    ArrayList<Ingredient> ingredients = new ArrayList<>();
                    Ingredient ingredient1 = new Ingredient();
                    ingredient1.setIngredient("ingredient1");
                    ingredient1.setMeasure("C");
                    ingredient1.setQuantity(2);
                    ingredients.add(ingredient1);
                    testReceipt.setIngredients(ingredients);
                    steps.add(step1);

                    Step step2 = new Step();
                    step2.setShortDescription("Step 2 shortDescription");
                    step2.setDescription("Step 2 description");
                    steps.add(step2);
                    testReceipt.setSteps(steps);


                    Intent intent = new Intent(InstrumentationRegistry.getTargetContext(), StepDetailActivity.class);
                    intent.putExtra(DetailActivity.RECEIPT_DATA, testReceipt);
                    intent.putExtra(StepDetailActivity.STEP_DATA, testReceipt.getSteps());
                    intent.putExtra(StepDetailActivity.INDEX_STEP_DATA, 0);
                    intent.putExtra(StepDetailActivity.INGREDIENT_DATA, testReceipt.getIngredients());
                    intent.putExtra(StepDetailActivity.IS_INGREDIENT, true);
                    return intent;
                }

            };
    @Before
    public void init(){
        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
        loadIngredientsDetailFragment();

    }
    @Before
    public void setup() {
//        loadStepDetailFragment();

    }
    public void loadIntent()
    {

    }
    @Test
    public void clickFragmenetExist() {
        onView(withId(R.id.master_detail_step_fragment)).check(matches(isDisplayed()));


    }



    public void loadStepDetailFragment(){
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        FragmentManager fragmentManager = mActivityTestRule.getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.master_detail_step_fragment, stepDetailFragment)
                .commit();

    }
    public void loadIngredientsDetailFragment(){
        IngredientsDetailFragment ingredientsDetailFragment = new IngredientsDetailFragment();
        FragmentManager fragmentManager = mActivityTestRule.getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.master_detail_step_fragment, ingredientsDetailFragment)
                .commit();

    }


}