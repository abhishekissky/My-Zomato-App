package com.example.myzomatoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    Context context;
    List<Restaurants>restaurantsList;

    public Adapter(Context context, List<Restaurants> restaurantsList) {
        this.context = context;
        this.restaurantsList = restaurantsList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.zomato_list,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.name.setText(restaurantsList.get(position).getRestaurant().getName());
        holder.locality.setText(restaurantsList.get(position).getRestaurant().getLocation().getLocality());
        holder.rating.setText(restaurantsList.get(position).getRestaurant().getLocation().getAddress());
    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        TextView name,rating,locality;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
            locality = itemView.findViewById(R.id.locality);
        }
    }
}
