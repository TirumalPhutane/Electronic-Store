package com.example.electronic_store.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.electronic_store.Entity.Categories;
import com.example.electronic_store.R;
import com.example.electronic_store.activity.ListActivity;
import com.example.electronic_store.utils.API;

import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {

    Context context;
    List<Categories> viewList;

    public HomeRecyclerAdapter(Context context, List<Categories> viewList) {
        this.context = context;
        this.viewList = viewList;
    }

    @NonNull
    @Override
    public HomeRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_home,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapter.MyViewHolder holder, int position) {
        Glide.with(context).load(API.BASE_URL+"/"+viewList.get(position).getImage()).into(holder.imageview);
        holder.textCategory.setText(viewList.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return viewList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageview;
        TextView textCategory;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview);
            textCategory = itemView.findViewById(R.id.textCategory);
            imageview.setClipToOutline(true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context, ListActivity.class);
                    intent.putExtra("category", viewList.get(getAdapterPosition()).getCategory());
                    String c = viewList.get(getAdapterPosition()).getCategory();
                    Log.e("Position", c);
                    context.startActivity(intent);
                    //context.startActivity(new Intent(context, ProductActivity.class));
                }
            });
        }
    }
}
