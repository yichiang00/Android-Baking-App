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

//
//public class StepListAdapter extends BaseAdapter {
//
//    // Keeps track of the context and list of images to display
//    private Context mContext;
//    private ArrayList<Step> mImageIds = new ArrayList<Step>();
//
//    /**
//     * Constructor method
//     * @param imageIds The list of images to display
//     */
//    public StepListAdapter(Context context, ArrayList<Step> imageIds) {
//        mContext = context;
//        mImageIds = imageIds;
//    }
//    public void setItems(ArrayList<Step> moviesList) {
//        mImageIds = moviesList;
//        notifyDataSetChanged();
//    }
//
//    /**
//     * Returns the number of items the adapter will display
//     */
//    @Override
//    public int getCount() {
//        if(mImageIds != null)
//        {
//            return mImageIds.size();
//
//        }
//        return 0;
//
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    /**
//     * Creates a new ImageView for each item referenced by the adapter
//     */
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        TextView textView = convertView.findViewById(R.id.receipt_main_title);
//        ImageView thumbnailUrlImageView = convertView.findViewById(R.id.images_grid_view);
////        if (convertView == null) {
////            // If the view is not recycled, this creates a new ImageView to hold an image
////            textView = new TextView(mContext);
////            thumbnailUrlImageView= new ImageView(mContext);
////        } else {
////            textView = (TextView) convertView;
////            thumbnailUrlImageView= new ImageView(mContext);
////
////        }
////        textView
//        // Set the image resource and return the newly created ImageView
//        textView.setText(mImageIds.get(position).getShortDescription());
//        return textView;
//    }
//
//}


public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepListViewHolder>  {

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    StepListFragment.OnImageClickListener mCallback;

    private ArrayList<Step>  mSteps= new ArrayList<Step>();
    public StepListAdapter(StepListFragment.OnImageClickListener rClickback) {
        mCallback=rClickback;
    }


    public void setItems(ArrayList<Step> itemList) {
        mSteps = itemList;
        notifyDataSetChanged();
    }


    @Override
    public StepListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.step_list_item, viewGroup, shouldAttachToParentImmediately);

        return new StepListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(StepListViewHolder holder, int position) {
        Context contextImage = holder.imageView.getContext();
        String imageUrl = mSteps.get(position).getThumbnailURL();
        if(imageUrl != null && imageUrl.length() > 0)
        {
            Picasso.with(contextImage)
                    .load(mSteps.get(position).getThumbnailURL())
                    .into(holder.imageView);

        }

        holder.titleTextiew.setText(mSteps.get(position).getShortDescription());
    }


    @Override
    public int getItemCount() {
        return mSteps.size();
    }




    class StepListViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView titleTextiew;
        ImageView imageView;


        public StepListViewHolder(View itemView) {
            super(itemView);
            titleTextiew = (TextView)itemView.findViewById(R.id.receipt_main_title);
            imageView = (ImageView) itemView.findViewById(R.id.receipt_thumbnailUrl);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mCallback.onImageSelected(clickedPosition);
//            Intent intent = new Intent(view.getContext(), DetailActivity.class);
//            Step cStep =  mSteps.get(clickedPosition);
//
//            intent.putExtra(DetailActivity.RECEIPT_DATA,cReceipt);
//            view.getContext().startActivity(intent);
        }

    }
}


