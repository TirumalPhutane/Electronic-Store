package com.example.electronic_store.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.electronic_store.Entity.OrderDetail;
import com.example.electronic_store.Entity.Orders;
import com.example.electronic_store.R;
import com.example.electronic_store.adapter.OrderRecyclerAdapter;
import com.example.electronic_store.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {

    RecyclerView recyclerview;
    OrderRecyclerAdapter orderRecyclerAdapter;
    List<OrderDetail> ordersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview = view.findViewById(R.id.recyclerview);
        ordersList = new ArrayList<>();
        orderRecyclerAdapter = new OrderRecyclerAdapter(getContext(),ordersList);
        recyclerview.setAdapter(orderRecyclerAdapter);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),1));
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserOrders();
    }

    private void getUserOrders() {
        ordersList.clear();

        int id = getContext().getSharedPreferences("electronic_store", Context.MODE_PRIVATE).getInt("id",0);

        RetrofitClient.getInstance().getApi().getOrderDetails(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().getAsJsonObject().get("status").getAsString().equals("success"))
                {
                    JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();

                    for (JsonElement element:jsonArray)
                    {
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setProduct_name(element.getAsJsonObject().get("product_name").getAsString());
                        orderDetail.setPrice(element.getAsJsonObject().get("price").getAsInt());
                        orderDetail.setOrderDate(element.getAsJsonObject().get("orderDate").getAsString());
                        orderDetail.setQuantity(element.getAsJsonObject().get("quantity").getAsInt());
                        orderDetail.setUid(element.getAsJsonObject().get("uid").getAsInt());
                        orderDetail.setImage(element.getAsJsonObject().get("image").getAsString());
                        ordersList.add(orderDetail);
                    }
                    orderRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}