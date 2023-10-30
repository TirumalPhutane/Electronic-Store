package com.example.electronic_store.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.electronic_store.Entity.OrderDetail;
import com.example.electronic_store.Entity.Orders;
import com.example.electronic_store.R;
import com.example.electronic_store.utils.API;

import java.util.List;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.MyViewHolder> {

    Context context;
    List<OrderDetail> ordersList;

    public OrderRecyclerAdapter(Context context, List<OrderDetail> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrderRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_order,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerAdapter.MyViewHolder holder, int position) {
        Glide.with(context).load(API.BASE_URL+"/"+ordersList.get(position).getImage()).into(holder.image);
        holder.textName.setText(ordersList.get(position).getProduct_name());
        holder.textPrice.setText("₹ "+ordersList.get(position).getPrice());
        holder.textOrderedOn.setText(ordersList.get(position).getOrderDate());
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView textName, textPrice, textOrderedOn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            textOrderedOn = itemView.findViewById(R.id.textOrderedOn);
        }
    }
}
