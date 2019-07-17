package com.example.kenthomeopathicpharmacy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapterTopSeller extends RecyclerView.Adapter<RecyclerAdapterTopSeller.ViewHolder>{

    private Context context;
    List<TopSeller> topSellerList = new ArrayList<>();

    public RecyclerAdapterTopSeller(Context context, List<TopSeller> topSellerList) {       //constructor
        this.context = context;
        this.topSellerList = topSellerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topseller_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemname.setText(topSellerList.get(position).getItemname());
        //Picasso.get().load(topSellerList.get(position).getPics()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return topSellerList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemname;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.medicine);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

