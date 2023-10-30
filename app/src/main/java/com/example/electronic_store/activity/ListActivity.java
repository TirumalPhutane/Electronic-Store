package com.example.electronic_store.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.electronic_store.Entity.Product;
import com.example.electronic_store.R;
import com.example.electronic_store.adapter.ProductListRecyclerAdapter;
import com.example.electronic_store.utils.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    TextView textCategory;
    Toolbar toolbar;
    RecyclerView recyclerview;
    ProductListRecyclerAdapter productListRecyclerAdapter;
    List<Product> productList;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        textCategory = findViewById(R.id.textCategory);
        toolbar = findViewById(R.id.toolbar);

        recyclerview = findViewById(R.id.recyclerview);
        productList = new ArrayList<>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.app4);
        getSupportActionBar().setTitle("");

        productListRecyclerAdapter = new ProductListRecyclerAdapter(this, productList);
        recyclerview.setAdapter(productListRecyclerAdapter);
        recyclerview.setLayoutManager(new GridLayoutManager(this,2));
        category = getIntent().getStringExtra("category");
        textCategory.setText(category);
        getListByCategory();
    }

    private void getListByCategory() {
        RetrofitClient.getInstance().getApi().getListbyCategory(category).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().getAsJsonObject().get("status").getAsString().equals("success"))
                {
                    JsonArray array = response.body().getAsJsonObject().get("data").getAsJsonArray();
                    for (JsonElement element:array) {
                        Product product = new Product();
                        product.setPid(element.getAsJsonObject().get("pid").getAsInt());
                        product.setCategory(element.getAsJsonObject().get("category").getAsString());
                        product.setPrice(element.getAsJsonObject().get("price").getAsInt());
                        product.setQuantity(element.getAsJsonObject().get("quantity").getAsInt());
                        product.setBrand(element.getAsJsonObject().get("brand").getAsString());
                        product.setProduct_name(element.getAsJsonObject().get("product_name").getAsString());
                        product.setModel_name(element.getAsJsonObject().get("model_name").getAsString());
                        product.setManufacture_country(element.getAsJsonObject().get("manufacture_country").getAsString());
                        product.setDescription(element.getAsJsonObject().get("description").getAsString());
                        product.setImage(element.getAsJsonObject().get("image").getAsString());
                        productList.add(product);
                    }
                    productListRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_list, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void filter(String text)
    {
        List<Product> filteredList = new ArrayList<>();
        for (Product item : productList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getProduct_name().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            productListRecyclerAdapter.filterList(filteredList);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}