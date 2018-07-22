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

    public static final String IMAGE_ID_LIST = "image_ids";
    public static final String LIST_INDEX = "list_index";

    private static final String TAG = "BodyPartFragment";

    private List<String> mTitleLists;
    private int mListIndex;


    public ReceiptNameCardFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            mTitleLists = savedInstanceState.getStringArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        final TextView titleTextView = (TextView) rootView.findViewById(R.id.receipt_main_title);

        if(mTitleLists != null){
            titleTextView.setText(mTitleLists.get(mListIndex));

            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

        } else {
            Log.v(TAG, "This fragment has a null list of image id's");
        }

        // Return the rootView
        return rootView;
    }


    public void setImageIds(List<String> imageIds) {
        mTitleLists = imageIds;
    }

    public void setListIndex(int index) {
        mListIndex = index;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putStringArrayList(IMAGE_ID_LIST, (ArrayList<String>) mTitleLists);
        currentState.putInt(LIST_INDEX, mListIndex);
    }


}
