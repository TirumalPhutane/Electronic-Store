package com.example.electronic_store.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.electronic_store.Entity.Product;
import com.example.electronic_store.Entity.Quantity;
import com.example.electronic_store.Entity.User;
import com.example.electronic_store.R;
import com.example.electronic_store.utils.API;
import com.example.electronic_store.utils.RetrofitClient;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.MyViewHolder> {

    public interface IMyinterface{

        public void refresh();
    }

    Context context;
    List<Product> productList;
    IMyinterface listener;
    int q;

    public CartRecyclerAdapter(Context context, List<Product> productList,IMyinterface listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_cart, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerAdapter.MyViewHolder holder, int position) {
        Glide.with(context).load(API.BASE_URL+"/"+productList.get(position).getImage()).into(holder.image);
        holder.textName.setText(productList.get(position).getProduct_name());
        holder.textPrice.setText("₹ "+productList.get(position).getPrice());
        holder.textQuantity.setText(""+productList.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;
        TextView textName, textPrice, textQuantity;
        Button btnPlus, btnMinus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            textQuantity = itemView.findViewById(R.id.textQuantity);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);

            btnPlus.setOnClickListener(this);
            btnMinus.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            //listener.refresh();
            if(v.getId()==R.id.btnPlus)
            {
                int cid = productList.get(getAdapterPosition()).getPid();

                q = Integer.valueOf((String)textQuantity.getText());
                q += 1;

                int uid = context.getSharedPreferences("electronic_store", Context.MODE_PRIVATE).getInt("id",0);
                Quantity qty = new Quantity();
                qty.setQuantity(q);
                qty.setUid(uid);
//                listener.refresh();

                RetrofitClient.getInstance().getApi().updateCart(cid, qty).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

                textQuantity.setText(""+q);

            }
            else if (v.getId()==R.id.btnMinus)
            {
                int cid = productList.get(getAdapterPosition()).getPid();
                q = Integer.valueOf((String)textQuantity.getText());
                q -= 1;

                int uid = context.getSharedPreferences("electronic_store", Context.MODE_PRIVATE).getInt("id",0);
                Quantity qty2 = new Quantity();
                qty2.setQuantity(q);
                qty2.setUid(uid);

                Quantity qty3 = new Quantity();
                qty3.setUid(uid);
               // listener.refresh();

                if(q>=1)
                {
                    RetrofitClient.getInstance().getApi().updateCart(cid, qty2).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                    textQuantity.setText(""+q);

                }
                else if(q<1)
                {
                    RetrofitClient.getInstance().getApi().deleteCart(cid, qty3).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            listener.refresh();
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }
}
