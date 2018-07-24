package com.example.android.android_me.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.android_me.R;
import com.example.android.android_me.model.Ingredient;
import com.example.android.android_me.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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


public class IngedientListAdapter extends RecyclerView.Adapter<IngedientListAdapter.IngedientListViewHolder>  {


    private ArrayList<Ingredient>  mIngredients= new ArrayList<Ingredient>();
    public IngedientListAdapter() {
    }


    public void setItems(ArrayList<Ingredient> itemList) {
        mIngredients = itemList;
        notifyDataSetChanged();
    }


    @Override
    public IngedientListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.fragment_detail_ingredients, viewGroup, shouldAttachToParentImmediately);

        return new IngedientListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(IngedientListViewHolder holder, int position) {

        holder.ingedientTextiew.setText(mIngredients.get(position).getIngredient());
        holder.measureTextiew.setText(mIngredients.get(position).getMeasure());
        holder.quantityTextiew.setText(Float.toString(mIngredients.get(position).getQuantity()));
    }


    @Override
    public int getItemCount() {
        return mIngredients.size();
    }




    class IngedientListViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView ingedientTextiew;
        TextView measureTextiew;
        TextView quantityTextiew;


        public IngedientListViewHolder(View itemView) {
            super(itemView);
            ingedientTextiew = (TextView)itemView.findViewById(R.id.receipt_ingredient_name);
            measureTextiew = (TextView)itemView.findViewById(R.id.receipt_ingredient_measure);
            quantityTextiew = (TextView)itemView.findViewById(R.id.receipt_ingredient_quantity);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();

        }

    }
}


