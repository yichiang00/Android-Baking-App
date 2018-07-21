package com.example.android.android_me.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.android.android_me.R;
import com.example.android.android_me.model.Receipt;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;


public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.MovieViewHolder>  {

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    private ArrayList<Receipt> mReceipts= new ArrayList<Receipt>();
    public MasterListAdapter() {

    }


    public void setMovieItems(ArrayList<Receipt> moviesList) {
        mReceipts = moviesList;
        notifyDataSetChanged();
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.receipt_list_item, viewGroup, shouldAttachToParentImmediately);

        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Context contextImage = holder.imageView.getContext();
        String imageUrl = mReceipts.get(position).getImage();
        if(imageUrl != null && imageUrl.length() > 0)
        {
            Picasso.with(contextImage)
                    .load(mReceipts.get(position).getImage())
                    .into(holder.imageView);

        }

        holder.nameTextiew.setText(mReceipts.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return mReceipts.size();
    }




    class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView nameTextiew;
        ImageView imageView;


        public MovieViewHolder(View itemView) {
            super(itemView);
            nameTextiew = (TextView)itemView.findViewById(R.id.receipt_title);
            imageView = (ImageView) itemView.findViewById(R.id.receipt_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

        }


    }
}


