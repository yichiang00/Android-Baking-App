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
        if( mIngredients != null){
            return mIngredients.size();
        }else{
            return 0;
        }
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


