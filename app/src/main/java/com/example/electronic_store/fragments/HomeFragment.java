package com.example.electronic_store.fragments;

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

import com.example.electronic_store.Entity.Categories;
import com.example.electronic_store.R;
import com.example.electronic_store.adapter.HomeRecyclerAdapter;
import com.example.electronic_store.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView recyclerview;
    HomeRecyclerAdapter homeRecyclerAdapter;
    List<Categories> viewList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewList = new ArrayList<>();
        recyclerview = view.findViewById(R.id.recyclerview);
        homeRecyclerAdapter = new HomeRecyclerAdapter(getContext(), viewList);
        recyclerview.setAdapter(homeRecyclerAdapter);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));

        getCategories();
    }

    private void getCategories() {
        viewList.clear();
        RetrofitClient.getInstance().getApi().getAllCategories().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.body().getAsJsonObject().get("status").getAsString().equals("success"))
                {
                    JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();

                    for(JsonElement element:jsonArray)
                    {
                        Categories categories = new Categories();
                        categories.setImage(element.getAsJsonObject().get("image").getAsString());
                        categories.setCategory(element.getAsJsonObject().get("category").getAsString());
                        viewList.add(categories);
                    }
                    homeRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


}