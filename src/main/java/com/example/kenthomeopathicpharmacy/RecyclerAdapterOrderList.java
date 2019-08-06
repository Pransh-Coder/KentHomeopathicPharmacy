package com.example.kenthomeopathicpharmacy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecyclerAdapterOrderList extends RecyclerView.Adapter<RecyclerAdapterOrderList.ViewHolder> {

    Context context;
    List<Order> orderList = new ArrayList<>();

    public RecyclerAdapterOrderList(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.itemName.setText(orderList.get(position).getName());
        holder.itemPrice.setText("Rs." + orderList.get(position).getPrice());
        holder.textView.setText("Delievery "+orderList.get(position).getDelivery());
        Picasso.get().load(orderList.get(position).getImg());

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        holder.date.setText(currentDate);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textView.setText("order:"+orderList.get(position).getStatus());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemPrice,textView,date;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.prod_name);
            itemPrice = itemView.findViewById(R.id.prod_price);
            textView=itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date);
            button=itemView.findViewById(R.id.cnclorder);
        }
    }
}
