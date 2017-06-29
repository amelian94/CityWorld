package com.neonidapps.cityworld.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neonidapps.cityworld.R;
import com.neonidapps.cityworld.models.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Neonidas on 07/03/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private List<Place> places;

    private Activity activity;
    private OnItemClickListener itemClickListener;


    public RecyclerViewAdapter(List<Place> places, Activity activity, OnItemClickListener itemClickListener) {
        this.places = places;
        this.activity = activity;
        this.itemClickListener=itemClickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(places.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }
    // Return the size of your dataset (invoked by the layout manager)

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageViewPicture;
        public TextView textViewName;
        public TextView textViewDesc;
        public TextView textViewRate;
        public TextView textViewDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewPicture = (ImageView) itemView.findViewById(R.id.imageViewPicture);
            textViewName= (TextView) itemView.findViewById(R.id.textViewName);
            textViewDesc= (TextView) itemView.findViewById(R.id.textViewDesc);
            textViewRate= (TextView) itemView.findViewById(R.id.textViewRate);
            textViewDelete= (TextView) itemView.findViewById(R.id.textViewDelete);
        }

        public void bind(final Place place, final OnItemClickListener itemClickListener) {

            this.textViewName.setText(place.getName());
            this.textViewDesc.setText(place.getDesc());
            this.textViewRate.setText(place.getRate() +"");

            if (!place.getPicture().isEmpty())
                Picasso.with(activity).load(place.getPicture()).fit().into(imageViewPicture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(place,getAdapterPosition());
                }
            });

            this.textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onDeleteClick(place,getAdapterPosition());
//                    places.remove(getAdapterPosition());
//                    notifyDataSetChanged();

                }
            });
        }

    }

    public interface OnItemClickListener{
        void onItemClick(Place place, int position);
        void onDeleteClick(Place place, int position);
    }

}
