package com.example.android.android_me.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.android_me.R;
import com.example.android.android_me.model.Receipt;
import com.example.android.android_me.model.Step;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class StepListAdapter extends BaseAdapter {

    // Keeps track of the context and list of images to display
    private Context mContext;
    private ArrayList<Step> mImageIds = new ArrayList<Step>();

    /**
     * Constructor method
     * @param imageIds The list of images to display
     */
    public StepListAdapter(Context context, ArrayList<Step> imageIds) {
        mContext = context;
        mImageIds = imageIds;
    }
    public void setItems(ArrayList<Step> moviesList) {
        mImageIds = moviesList;
        notifyDataSetChanged();
    }

    /**
     * Returns the number of items the adapter will display
     */
    @Override
    public int getCount() {
        if(mImageIds != null)
        {
            return mImageIds.size();

        }
        return 0;

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Creates a new ImageView for each item referenced by the adapter
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // If the view is not recycled, this creates a new ImageView to hold an image
            textView = new TextView(mContext);
        } else {
            textView = (TextView) convertView;
        }

        // Set the image resource and return the newly created ImageView
        textView.setText(mImageIds.get(position).getShortDescription());
        return textView;
    }

}
