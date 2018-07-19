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
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.android.android_me.model.Receipt;

import java.util.ArrayList;
import java.util.List;


public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.ReceiptCardViewHolder> {

    // Keeps track of the context and list of images to display
    private Context mContext;
    private ArrayList<Receipt> mReceipts;

    /**
     * Constructor method
     * @param receipts The list of images to display
     */
    public MasterListAdapter(Context context, ArrayList<Receipt> receipts) {
        mContext = context;
        mReceipts = receipts;
    }
    public void setReceiptItems(ArrayList<Receipt> receiptList) {
        mReceipts = receiptList;
        notifyDataSetChanged();
    }
    /**
     * Returns the number of items the adapter will display
     */
//    @Override
//    public int getCount() {
//        return mReceipts.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }

    @Override
    public ReceiptCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = new ImageView(parent.getContext());
        ReceiptCardViewHolder viewHolder = new ReceiptCardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReceiptCardViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Creates a new ImageView for each item referenced by the adapter
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // If the view is not recycled, this creates a new ImageView to hold an image
            imageView = new ImageView(mContext);
            // Define the layout parameters
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        // Set the image resource and return the newly created ImageView
//        imageView.setImageResource(mReceipts.get(position));
        return imageView;
    }
    class ReceiptCardViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView listItemNumberView;
        TextView viewHolderIndex;
        ImageView movieImageView;


        public ReceiptCardViewHolder(View itemView) {
            super(itemView);
//            movieImageView = (ImageView) itemView.findViewById(R.id.image_iv);
//            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
//            int clickedPosition = getAdapterPosition();
//            Intent intent = new Intent(view.getContext(), DetailActivity.class);
//            Movie cMovie =  mMovies.get(clickedPosition);
//            intent.putExtra(DetailActivity.MOVIE_KEY, cMovie );
//            view.getContext().startActivity(intent);
        }


    }
}