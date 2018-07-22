package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.android_me.R;

import java.util.ArrayList;
import java.util.List;

public class ReceiptNameCardFragment extends Fragment {

    // Final Strings to store state information about the list of images and list index
    public static final String IMAGE_ID_LIST = "image_ids";
    public static final String LIST_INDEX = "list_index";

    // Tag for logging
    private static final String TAG = "BodyPartFragment";

    // Variables to store a list of image resources and the index of the image that this fragment displays
    private List<String> mTitleLists;
    private int mListIndex;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public ReceiptNameCardFragment() {
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Load the saved state (the list of images and list index) if there is one
        if(savedInstanceState != null) {
            mTitleLists = savedInstanceState.getStringArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        // Get a reference to the ImageView in the fragment layout
        final TextView titleTextView = (TextView) rootView.findViewById(R.id.receipt_main_title);

        // If a list of image ids exists, set the image resource to the correct item in that list
        // Otherwise, create a Log statement that indicates that the list was not found
        if(mTitleLists != null){
            // Set the image resource to the list item at the stored index
            titleTextView.setText(mTitleLists.get(mListIndex));

            // Set a click listener on the image view
            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    // Increment position as long as the index remains <= the size of the image ids list
//                    if(mListIndex < mTitleLists.size()-1) {
//                        mListIndex++;
//                    } else {
//                        // The end of list has been reached, so return to beginning index
//                        mListIndex = 0;
//                    }
//                    // Set the image resource to the new list item
//                    titleTextView.setText(mTitleLists.get(mListIndex));
                }
            });

        } else {
            Log.v(TAG, "This fragment has a null list of image id's");
        }

        // Return the rootView
        return rootView;
    }

    // Setter methods for keeping track of the list images this fragment can display and which image
    // in the list is currently being displayed

    public void setImageIds(List<String> imageIds) {
        mTitleLists = imageIds;
    }

    public void setListIndex(int index) {
        mListIndex = index;
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putStringArrayList(IMAGE_ID_LIST, (ArrayList<String>) mTitleLists);
        currentState.putInt(LIST_INDEX, mListIndex);
    }


}

